package com.xuecheng.content.api;

import com.alibaba.fastjson.JSON;
import com.xuecheng.content.model.dto.CourseBaseDto;
import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CoursePublish;
import com.xuecheng.content.service.CoursePublishService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 课程发布 前端控制器
 * </p>
 *
 * @author itcast
 */
@Slf4j
@RestController
public class CoursePublishController {

    @Autowired
    private CoursePublishService coursePublishService;


    @ApiOperation("课程预览")
    @GetMapping("/coursepreview/{courseId}")
    public ModelAndView preview(@PathVariable("courseId") Long courseId) {

        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(courseId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("model", coursePreviewInfo);
        modelAndView.setViewName("course_template~");
        return modelAndView;
    }

    @ApiOperation("提交审核")
    @PostMapping("/courseaudit/commit/{courseId}")
    public void commitAudit(@PathVariable("courseId") Long courseId) {
        Long companyId = 1232141425L;
        coursePublishService.commitAudit(companyId, courseId);
    }

    @ApiOperation("课程发布")
    @ResponseBody
    @PostMapping("/coursepublish/{courseId}")
    public void coursepublish(@PathVariable("courseId") Long courseId) {
        Long companyId = 1232141425L;
        coursePublishService.coursepublish(companyId, courseId);
    }

    @ApiOperation("课程下架")
    @ResponseBody
    @GetMapping("/courseoffline/{courseId}")
    public void courseoffline(@PathVariable("courseId") Long courseId) {
        Long companyId = 1232141425L;
        coursePublishService.courseoffline(companyId, courseId);
    }

    @ApiOperation("查询课程发布信息")
    @ResponseBody
    @GetMapping("/r/coursepublish/{courseId}")
    public CoursePublish getCoursepublish(@PathVariable("courseId") Long courseId) {
        CoursePublish coursePublish = coursePublishService.getCoursePublish(courseId);
        return coursePublish;
    }

    @ApiOperation("获取课程发布信息")
    @ResponseBody
    @GetMapping("/course/whole/{courseId}")
    public CoursePreviewDto getCoursePublish(@PathVariable("courseId") Long courseId) {


        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        CoursePublish coursePublish = coursePublishService.getCoursePublishCache(courseId);
        if (coursePublish == null) {
            return coursePreviewDto;
        }

        //课程基本信息
        CourseBaseDto courseBase = new CourseBaseDto();
        BeanUtils.copyProperties(coursePublish,courseBase);
        coursePreviewDto.setCourseBase(courseBase);

        //课程计划
        String teachplansJson = coursePublish.getTeachplan();
        List<TeachplanDto> teachplanDtos = JSON.parseArray(teachplansJson, TeachplanDto.class);
        coursePreviewDto.setTeachplans(teachplanDtos);

        return coursePreviewDto;

    }
}
