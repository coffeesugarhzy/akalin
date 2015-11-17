package com.sunspot.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Valid;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.ValidExt;
import com.sunspot.service.ValidService;

/**
 * 商家认证实现
 * 
 * @author qinkechun
 * 
 */
@Service
public class ValidServiceImpl implements ValidService{
	
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(FarmServiceImpl.class);

    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;
    
	public void add(HttpServletRequest request, Valid valid) {
	   Valid result=null;
	   String id=valid.getId();
	    if(id==null||"".equals(id)){//当无ID时
			valid.setAddDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss")); 
			String frontCover=Utils.uploadValidImages(request, "front","valid", valid.getUid());
			valid.setFrontcover(frontCover);
			String backCover=Utils.uploadValidImages(request, "back", "valid",valid.getUid());
			valid.setBackcover(backCover);
	        valid.setIsValid(0);
			baseDao.add(valid);//新增
	    }else{
	    	result=queryById(id);
	    	if(result!=null){
				valid.setAddDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss")); 
				String frontCover=Utils.uploadValidImages(request, "front","valid", valid.getUid());
				valid.setFrontcover(frontCover);
				String backCover=Utils.uploadValidImages(request, "back", "valid",valid.getUid());
				valid.setBackcover(backCover);
		        valid.setIsValid(0);//重新认证
		        valid.setRemark("");
		        valid.setValidDate("");
		        valid.setValidor("");
		        modify(valid);//重新更新
	    	}
	    }
	    
	}

	
	@Override
	public Valid queryValid(Valid valid) {
       try{
    	   Valid entity= baseDao.queryForObject("select * from valid where uid=? and type=?", new Object[]{valid.getUid(),valid.getType()}, Valid.class) ;
           return entity ; 
       }catch(Exception e){
           runLog.error(e.getMessage()) ; 
       }
       return null ; 
	}

	@Override
    public Valid queryById(String id)
    { 
        Valid valid = baseDao.getByHibernate(Valid.class, id) ;  
        return valid ; 
    }

    @Override
    @Transactional
	public int modify(Valid t) {
        try{          
            //根据id查询用信息
            Valid modify = baseDao.getByHibernate(Valid.class, t.getId()) ;
            modify.setFrontcover(t.getFrontcover());
            modify.setBackcover(t.getBackcover());
            modify.setAddDate(t.getAddDate());
            modify.setIsValid(t.getIsValid());//重新验证
            modify.setRemark(t.getRemark());
            modify.setValidDate(t.getValidDate());
            modify.setValidor(t.getValidor());
            baseDao.update(modify) ; 
            return 1 ; 
        }catch(Exception e){
            return 0 ; 
        } 
	}
    

	@Override
	public void queryTakeout(DataGridModel<ValidExt> list, String keyword,int status) {
        String countsql = "select count(*) from valid a,user_info b where a.uid=b.user_id ";
        String querysql = "select a.id,a.shopname,a.type,a.isValid,a.addDate,a.validor,a.validDate,b.telphone from valid a,user_info b where a.uid=b.user_id ";
        Object[] countParam = null;
        Object[] queryParam = null;

        switch (status)
        {
			case 0:
				countsql += " and a.isValid=0";
				querysql += " and a.isValid=0";
				break;
			case 1:
				countsql += " and a.isValid=1";
				querysql += " and a.isValid=1";
				break;
			case 2:
				countsql += " and a.isValid=2";
				querysql += " and a.isValid=2";
				break;
			default:
				break;
        }

        if (StringUtils.isNotBlank(keyword))
        {
            String keyfmt = Utils.getKeyword(keyword);
            countsql += " and ( a.shopname like ? or b.telphone like ?)";
            querysql += " and ( a.shopname like ? or b.telphone like ?) order by a.addDate desc limit ?,?";
            countParam = new Object[]
            { keyfmt,keyfmt};
            queryParam = new Object[]
            { keyfmt,keyfmt,list.getCurNum(), list.getRows() };
        }
        else
        {
            querysql += " order by a.addDate limit ?,?";
            queryParam = new Object[]
            { list.getCurNum(), list.getRows() };
        }

        int total = (null == countsql) ? baseDao.queryForIntPage(countsql)
                : baseDao.queryForIntPage(countsql, countParam);

        list.setCount(total);

        // 查询返利
        List<ValidExt> validExtList = baseDao.query(querysql, queryParam,ValidExt.class);    
        list.setContent(validExtList);
		
	}


	@Override
	public ValidExt queryExtById(String id) {
        String querysql="select a.id,a.shopname,a.type,a.isValid,a.addDate,a.validor,a.validDate,a. frontcover,a.backcover,a.remark,b.telphone,b.logo from valid a,user_info b where a.uid=b.user_id and a.id=?";
        try{
        	ValidExt entity= baseDao.queryForObject(querysql, new Object[]{id}, ValidExt.class) ;
            return entity ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ; 
        }
        return null ; 
	}


	public List<Valid> isValidResult(Valid valid){
		 String querysql="select * from valid where uid=? and isValid=?";
		 List<Valid> list = baseDao.query(querysql, new Object[]{valid.getUid(),valid.getIsValid()},Valid.class);  
		 return list;
	}


	public Boolean isUserPassedValid(String uid) {
		Valid valid=new Valid();
		valid.setUid(uid);
		valid.setIsValid(1);
		List<Valid> list=isValidResult(valid);
        if(list.size()<2){
        	return false;
        }
        
        if(list.size()==2){
        	if((list.get(0).getType()==2&&list.get(1).getType()==3)||
        			(list.get(0).getType()==3&&list.get(1).getType()==2)){
        		return true;
        	}else{
        		return false;
        	}
        }
        
		if(list.size()==3){
			return true;
		}
		
		return false;
	}

}
