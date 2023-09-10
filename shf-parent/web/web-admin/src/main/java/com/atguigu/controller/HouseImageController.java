package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.HouseService;
import com.atguigu.util.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @className: HouseImageController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseService houseService;

    private static final String PAGE_UPLOAD = "house/upload";


    /**
     * @Description 跳转到上传页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @param type
     * @return ModelAndView
     */
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public ModelAndView uploadPage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type) {
        log.debug("需要上传的图片的房源 houseId={}  type={}", houseId, type);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_UPLOAD);

        mv.addObject("houseId", houseId);
        mv.addObject("type", type);

        return mv;
    }

    /**
     * @Description 处理上传图片, 并且保存到数据库和设置默认房源图片
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @param type
     * @param files
     * @return Result
     */
    @ResponseBody
    @PostMapping("/upload/{houseId}/{type}")
    public synchronized Result uploadFile2Qiniu(@PathVariable("houseId") Long houseId,
                                                @PathVariable("type") Integer type,
                                                @RequestParam("file") List<MultipartFile> files) {

        log.debug("文件上传处理  houseId={} type={} files={}", houseId, type, files.get(0).getOriginalFilename());

        //创建日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String yearMonth = sdf.format(new Date());

        if (!CollectionUtils.isEmpty(files)) {
            for (int j = 0; j < files.size(); j++) {
                MultipartFile mFile = files.get(j);

                //1. 上传文件目录+文件名
                String fileName = "shf-images/" + yearMonth + "/" + UUID.randomUUID().toString() + "--" + mFile.getOriginalFilename();

                //2. 上传到七牛云
                try {
                    QiniuUtils.upload2Qiniu(mFile.getBytes(), fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("文件上传失败!");
                }

                //3. 获取七牛云图片访问url
                String qiniuImgUrl = QiniuUtils.getUrl(fileName);

                //4. 保存图片url到数据库
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setType(type);
                houseImage.setImageUrl(qiniuImgUrl);
                houseImage.setImageName(fileName);
                int rows = houseImageService.insert(houseImage);

                //判断是否添加成功
                if (rows <= 0) {
                    log.debug("图片保存失败!  imgUrl={} fileName={}", qiniuImgUrl, fileName);
                }

                //5. 设置房源的默认图片
                if (j == 0) {
                    int rows2 = houseService.updateDefaultImageUrlByHouseId(houseId, qiniuImgUrl);
                    log.debug("houseId={} 设置默认图片 url={}", houseId, qiniuImgUrl);
                }

            }
        }
        return Result.ok();
    }

    /**
     * @Description 根据图片id删除图片 且 删除房源中的默认图片
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param houseId
     * @return String
     */
    @RequestMapping("/deleteImage/{id}/{houseId}")
    public String deleteHouseImage(@PathVariable("id") Long id, @PathVariable("houseId") Long houseId) {
        log.debug("删除图片的id={}", id);

        int rows = houseImageService.deleteHouseImageAndDeleteDefaultHouseImageById(id);

        return "redirect:/house/detail/" + houseId;
    }

}
