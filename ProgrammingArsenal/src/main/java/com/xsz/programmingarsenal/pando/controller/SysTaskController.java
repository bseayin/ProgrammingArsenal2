package com.xsz.programmingarsenal.pando.controller;

import com.xsz.programmingarsenal.generatecode.jpa.base.BaseController;
import com.xsz.programmingarsenal.pando.entity.SysTask;
import com.xsz.programmingarsenal.pando.service.ISysTaskService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务 Controller
 *
 * @author Brad
 */ 
@RestController
@RequestMapping("/sysTask")
public class SysTaskController extends BaseController {

	@Resource
	private ISysTaskService sysTaskService;

	@GetMapping("/getById/{id}")
	public SysTask getById(@PathVariable Long id) {
		SysTask result = sysTaskService.findById(id);
		return result;
	}

	@DeleteMapping("/deleteById/{id}")
	public void deleteById(@PathVariable Long id) {
		sysTaskService.deleteById(id);
	}

	@PostMapping("/save")
	public SysTask save(@RequestBody SysTask model) {
		SysTask result = sysTaskService.save(model);
		return result;
	}

}
