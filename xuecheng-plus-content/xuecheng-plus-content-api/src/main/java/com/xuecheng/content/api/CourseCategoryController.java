package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程分类 前端控制器
 * </p>
 *
 * @author itcast
 */
@Slf4j
@RestController
@RequestMapping("/course-category")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService  courseCategoryService;

    @GetMapping("/tree-nodes")
    public List<CourseCategoryTreeDto> list(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.listCourseCategory("1");
        return courseCategoryTreeDtos;
    }
}
