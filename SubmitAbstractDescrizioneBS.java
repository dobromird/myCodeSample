package com.linksmt.fpap.businessService.abstractBS;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;

import com.linksmt.fpap.model.TbApdDesprogettoCa;
import com.linksmt.fpap.model.TbDinqDescrizioneinquadramentoCa;
import com.linksmt.fpap.model.TbDpfDescrizionepercorsoCa;
import com.linksmt.fpap.model.TbDpifoDespianoformativoCa;
import com.linksmt.fpap.model.TbDufDescrizioneunitaformCa;
import com.linksmt.fpap.services.TbApdDesprogettoCaService;
import com.linksmt.fpap.services.TbDinqDescrizioneinquadramentoCaService;
import com.linksmt.fpap.services.TbDpfDescrizionepercorsoCaService;
import com.linksmt.fpap.services.TbDufDescrizioneunitaformCaService;
import com.linksmt.fpap.services.pianoFormativo.TbDpifoDespianoformativoCaService;
import com.linksmt.fpap.utils.FondaHibernateDaoSupport;


public abstract class SubmitAbstractDescrizioneBS extends FondaHibernateDaoSupport{

  protected TbApdDesprogettoCaService descrizioneService=null;
  protected TbDinqDescrizioneinquadramentoCaService tbDinqDescrizioneinquadramentoCaService=null;
  protected TbDpfDescrizionepercorsoCaService  tbDpfDescrizionepercorsoCaService=null;
  protected TbDufDescrizioneunitaformCaService tbDufDescrizioneunitaformCaService=null;
  protected TbDpifoDespianoformativoCaService  tbDpifoDespianoformativoCaService=null;
  
  
  public void setTbDpifoDespianoformativoCaService(
  		TbDpifoDespianoformativoCaService tbDpifoDespianoformativoCaService) {
  	this.tbDpifoDespianoformativoCaService = tbDpifoDespianoformativoCaService;
  }

  public void setTbDinqDescrizioneinquadramentoCaService(
  		TbDinqDescrizioneinquadramentoCaService tbDinqDescrizioneinquadramentoCaService) {
  	this.tbDinqDescrizioneinquadramentoCaService = tbDinqDescrizioneinquadramentoCaService;
  }

  public void setTbDpfDescrizionepercorsoCaService(
  		TbDpfDescrizionepercorsoCaService tbDpfDescrizionepercorsoCaService) {
  	this.tbDpfDescrizionepercorsoCaService = tbDpfDescrizionepercorsoCaService;
  }

  public void setTbDufDescrizioneunitaformCaService(
  		TbDufDescrizioneunitaformCaService tbDufDescrizioneunitaformCaService) {
  	this.tbDufDescrizioneunitaformCaService = tbDufDescrizioneunitaformCaService;
  }

  public void setDescrizioneService(TbApdDesprogettoCaService descrizioneService) {
  	this.descrizioneService = descrizioneService;
  }

  /**
   * Carica la mappa delle descrizioni presenti nella tipologica
   * @param request 
   * */
  
  protected void inserisciDescrizioni(List listaDescriptionMap,List descriptionList) {
  	try {
  		if(descriptionList!=null)
  		{
  			Iterator itDescriptionList=descriptionList.iterator();
  			while (itDescriptionList.hasNext()) {
  				Object descrizione=itDescriptionList.next();
  					/*inserimento descrizione progetto*/
  				if(descrizione instanceof TbApdDesprogettoCa)
  				{
  					descrizioneService.insertUpdateDescrizione(
  												(TbApdDesprogettoCa)descrizione,
  												(listaDescriptionMap!=null && listaDescriptionMap.size()>0)
  												);
  
  				}
  				/*inserimento descrizione ruoli*/
  				else if(descrizione instanceof TbDinqDescrizioneinquadramentoCa)
  				{
  					
  					tbDinqDescrizioneinquadramentoCaService.insertUpdateDescrizione(
  							(TbDinqDescrizioneinquadramentoCa)descrizione);
  				}
  				
  				/*inserimento descrizione percorso*/
  				else if(descrizione instanceof TbDpfDescrizionepercorsoCa)
  				{
  					tbDpfDescrizionepercorsoCaService.insertUpdateDescrizione(
  							(TbDpfDescrizionepercorsoCa)descrizione,
  							(listaDescriptionMap!=null && listaDescriptionMap.size()>0));
  				}
  				/*inserimento descrizione unita formativa*/
  				else if(descrizione instanceof TbDufDescrizioneunitaformCa)
  				{
  					tbDufDescrizioneunitaformCaService.insertUpdateDescrizione(
  							(TbDufDescrizioneunitaformCa)descrizione,
  							(listaDescriptionMap!=null && listaDescriptionMap.size()>0));
  				
  				}/*inserimento descrizione piano formativo*/
  				else if(descrizione instanceof TbDpifoDespianoformativoCa)
  				{
  					tbDpifoDespianoformativoCaService.insertUpdateDescrizione(
  							(TbDpifoDespianoformativoCa)descrizione,
  							(listaDescriptionMap!=null && listaDescriptionMap.size()>0));
  				
  				}
  			}
  		}
  	} catch (Throwable e) {
  		logger.error("", e);
  		throw new HibernateException(e);
  	}
  		
  }//end metodo
  
}
