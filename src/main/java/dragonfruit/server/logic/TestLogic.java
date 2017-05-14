package dragonfruit.server.logic;

import dragonfruit.server.entity.Test;

import java.util.List;

/**
 * Created by xuyh at 2017/4/16 11:28.
 */
public interface TestLogic {
	/**
	 * 保存
	 * 
	 * @param test
	 * @return
	 */
	String save(Test test);

	/**
	 * 根据id删除
	 * 
	 * @param id
	 */
	void deleteById(String id);

	/**
	 * 根据id获取
	 * 
	 * @param id
	 * @return
	 */
	Test getById(String id);

	/**
	 * 获取所有
	 * 
	 * @return
	 */
	List<Test> getAll();

	/**
	 * 
	 * @param value
	 * @return
	 */
	Test getByValue(String value);
}
