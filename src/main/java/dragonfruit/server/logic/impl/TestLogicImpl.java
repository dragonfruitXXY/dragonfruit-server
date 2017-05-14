package dragonfruit.server.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dragonfruit.server.dao.TestDao;
import dragonfruit.server.entity.Test;
import dragonfruit.server.logic.TestLogic;

import java.util.List;

/**
 * Created by xuyh at 2017/4/16 11:29.
 */
@Component("testLogic")
public class TestLogicImpl implements TestLogic {
	@Autowired
	private TestDao testDao;

	public void setTestDao(TestDao testDao) {
		this.testDao = testDao;
	}

	@Override
	public Test getByValue(String value) {
		return testDao.getByValue(value);
	}

	@Override
	public String save(Test test) {
		testDao.save(test);
		return test.getId();
	}

	@Override
	public void deleteById(String id) {
		testDao.deleteById(id);
	}

	@Override
	public Test getById(String id) {
		return testDao.getById(id);
	}

	@Override
	public List<Test> getAll() {
		return testDao.getAll();
	}
}
