package com.xsz.programmingarsenal.pando.entity;

import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.util.Date;
import java.io.Serializable;

/**
 * 定时任务
 *
 * @author Brad
 */ 
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "t_sys_task")
public class SysTask implements Serializable {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 创建人
	 */ 
	@Column(name = "create_by")
	private Long createBy;

	/**
	 * 创建时间/注册时间
	 */ 
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 最后更新人
	 */ 
	@Column(name = "modify_by")
	private Long modifyBy;

	/**
	 * 最后更新时间
	 */ 
	@Column(name = "modify_time")
	private Date modifyTime;

	/**
	 * 是否允许并发
	 */ 
	@Column(name = "concurrent")
	private Byte concurrent;

	/**
	 * 定时规则
	 */ 
	@Column(name = "cron")
	private String cron;

	/**
	 * 执行参数
	 */ 
	@Column(name = "data")
	private String data;

	/**
	 * 是否禁用
	 */ 
	@Column(name = "disabled")
	private Byte disabled;

	/**
	 * 执行时间
	 */ 
	@Column(name = "exec_at")
	private Date execAt;

	/**
	 * 执行结果
	 */ 
	@Column(name = "exec_result")
	private String execResult;

	/**
	 * 执行类
	 */ 
	@Column(name = "job_class")
	private String jobClass;

	/**
	 * 任务组名
	 */ 
	@Column(name = "job_group")
	private String jobGroup;

	/**
	 * 任务名
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 任务说明
	 */ 
	@Column(name = "note")
	private String note;

}

