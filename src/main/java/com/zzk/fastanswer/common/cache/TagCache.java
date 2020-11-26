package com.zzk.fastanswer.common.cache;

import com.zzk.fastanswer.model.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Tag缓存，没有存入数据库
 *
 * @author zzk
 * @create 2020-11-23 21:35
 */
public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTagList(Arrays.asList("java", "c", "cpp", "python", "html", "javascript", "typescript", "css", "node.js", "less", "sass", "golang", "matlab", "c#", "php", "objective-c", "shell", "scala"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTagList(Arrays.asList("spring boot", "spring cloud", "spring", "spring mvc", "mybatis", "ssm"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTagList(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "tomcat", "hadoop", "windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTagList(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "sqlserver"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTagList(Arrays.asList("intellij-idea", "eclipse", "maven", "git", "github", "vscode", "vim", "sublime", "visual-studio", "ide"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    public static String removeDuplication(String tag) {
        String[] split = StringUtils.split(tag, ",");
        Set<String> set = new HashSet<>(Arrays.asList(split));
        return StringUtils.join(set.toArray(new String[set.size()]), ",");
    }
}
