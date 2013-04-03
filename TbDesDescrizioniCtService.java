package com.linksmt.fpap.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.linksmt.fpap.model.TbDesDescrizioniCt;
import com.linksmt.fpap.model.TbDesDescrizioniCtId;
import com.linksmt.fpap.utils.FondaHibernateDaoSupport;

public class TbDesDescrizioniCtService extends FondaHibernateDaoSupport{  

  /* @Recupero tutte le tipologie di descrizione */
  public List getAllDescrizioni() throws Exception {
  	List list=null;
  	try{	
  		Session session = this.getSession();
          Criteria criteria = session.createCriteria(TbDesDescrizioniCt.class)
  			.addOrder(Order.desc("id.desCschCodNumeroFkPk"))
  			.addOrder(Order.asc("id.desSeqDescrizionePk"));
          list = criteria.list();
  	}
  	catch (Exception e) {
  		logger.error("",e);
  		throw e;
  	}
  	return list;
  }
  /* @Recupero lista descrizione per codice scheda */
  public List getDescrizioniPerCodiceScheda(int codCsch)throws Exception {
  	List list=null;
  	try{	
  		Session session = this.getSession();
          Criteria criteria = session.createCriteria(TbDesDescrizioniCt.class)
              .add(Restrictions.eq("id.desCschCodNumeroFkPk", codCsch))
  			.addOrder(Order.desc("id.desCschCodNumeroFkPk"))
  			.addOrder(Order.asc("id.desSeqDescrizionePk"));
             
          list = criteria.list();
  	} catch (Exception e) {
  		logger.error("",e);
  		throw e;
  	}
  	return list;
  }
  
  public TbDesDescrizioniCt getDescrizioniById(TbDesDescrizioniCtId tbDesDescrizioniCtId) throws Exception{
  	return (TbDesDescrizioniCt)this.getSession().get(TbDesDescrizioniCt.class, tbDesDescrizioniCtId);
  }
}
