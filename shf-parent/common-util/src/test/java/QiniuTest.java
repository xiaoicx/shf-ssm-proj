import com.atguigu.util.QiniuUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @className: QiniuTest
 * @Package: PACKAGE_NAME

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public class QiniuTest {

    @Test
    public void testUploadLocalFile() {
        File file = new File("C:\\Users\\xiaoqi\\Desktop\\DefaultFormattingConversionService.png");
        QiniuUtils.upload2Qiniu(file.getAbsolutePath(), file.getName());
    }

    @Test
    public void testUploadByte() throws IOException {
        File file = new File("C:\\Users\\xiaoqi\\Desktop\\DefaultFormattingConversionService.png");
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[fis.available()];
        fis.read(buf);
        fis.close();
        String uuid = UUID.randomUUID().toString();
        QiniuUtils.upload2Qiniu(buf, uuid + ".png");
    }

    @Test
    public void delete(){
        QiniuUtils.deleteFileFromQiniu("1ee7c1c8-05a8-44b7-9d07-524186a8d542.png");
    }
}
