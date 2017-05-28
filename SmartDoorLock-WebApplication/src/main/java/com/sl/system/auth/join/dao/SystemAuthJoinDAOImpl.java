package com.sl.system.auth.join.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.app.access.join.dao.AppAccessJoinDAOImpl;
import com.sl.system.auth.join.vo.SystemAuthJoinVO;

@Repository
public class SystemAuthJoinDAOImpl implements SystemAuthJoinDAO{

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	final private String NS = "mapper.com.sl.system.auth.join.";
	
	@Override
	public int updateMemberState(SystemAuthJoinVO vo) {
		// TODO Auto-generated method stub
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(AppAccessJoinDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		int result = sqlSession.update(NS+"updateMemberState", vo);
		sqlSession.delete(NS+"deleteMemberNoneAuth", vo);
		platformTransactionManager.commit(status);
		return result;
	}

	@Override
	public int deleteMember(SystemAuthJoinVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(NS+"deleteMember", vo);
	}

}
