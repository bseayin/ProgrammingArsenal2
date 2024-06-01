package com.xsz.programmingarsenal.pando.service.impl;

import com.xsz.programmingarsenal.generatecode.jpa.base.BaseServiceImpl;
import com.xsz.programmingarsenal.pando.entity.SysTask;
import com.xsz.programmingarsenal.pando.service.ISysTaskService;
import com.xsz.programmingarsenal.pando.dao.SysTaskDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 定时任务 接口实现
 *
 * @author Brad
 */ 
@Service
public class SysTaskServiceImpl extends BaseServiceImpl<SysTask> implements ISysTaskService {

	@Resource
	private SysTaskDao sysTaskDao;

}
