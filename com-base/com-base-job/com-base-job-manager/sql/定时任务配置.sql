/*按天统计资源总数，每天1点-7点统计，防止数据库错误导致无法统计*/
delete from `SCHEDULER_CONFIG` where JOB_ID = 'TEST_JOB_ID' and TRIGGER_ID = 'TRIGGER_TEST_JOB_ID' and GROUP_ID = 'TEST';
INSERT INTO `SCHEDULER_CONFIG` VALUES ('TEST_JOB_ID', 'TRIGGER_TEST_JOB_ID', 'TEST', 'com.base.job.test.TestJob', '*/5 0 * * * ?', '测试', '0', 'RUN', NULL, '2015-6-23 09:37:11', 'admin', '2015-6-23 09:37:23', 'admin', NULL);
commit;