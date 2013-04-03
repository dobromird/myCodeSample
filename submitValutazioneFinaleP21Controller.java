package com.linksmt.fpap.controllers.acquisizione.percorsoformativo;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.linksmt.fpap.authentication.FpapUser;
import com.linksmt.fpap.businessService.acquisizione.percorsoformativo.SubmitValutazioneFinaleP21BS;
import com.linksmt.fpap.command.PercorsoFormativoCommand;
import com.linksmt.fpap.controllers.abstractController.submitAbstractPercorsoFormativoController;
import com.linksmt.fpap.model.TbPefPercorsoformativoCl;
import com.linksmt.fpap.utils.Constants;
import com.linksmt.fpap.utils.Utility;

public class submitValutazioneFinaleP21Controller extends submitAbstractPercorsoFormativoController {
  
   protected final Log logger = LogFactory.getLog(getClass());
   
   protected SubmitValutazioneFinaleP21BS submitValutazioneFinaleP21BS;
   
  public void setSubmitValutazioneFinaleP21BS(
  	SubmitValutazioneFinaleP21BS submitValutazioneFinaleP21BS) {
  this.submitValutazioneFinaleP21BS = submitValutazioneFinaleP21BS;
}

  protected Map referenceData(HttpServletRequest request) throws Exception {	

        logger.info("submitValutazioneFinaleP21Controller >>> referenceData  caratteristiche valutazione finale called."); 
        try {
          PercorsoFormativoCommand percorsoFormativoCommand= (PercorsoFormativoCommand) WebUtils.getSessionAttribute(request, "sessionCommand");
       
          /* Caricamento descrizioni */
          super.caricaMappaDescrizioni(request,Constants.TB_CSCH_COD_SCHEDA_VALUTAZIONE_FINALE,percorsoFormativoCommand);
        }catch(Exception e) {
  		logger.error("", e);
  		request.setAttribute("error",  getMessageSourceAccessor().getMessage("load.error"));
  	}    
      return null;
      
    }

  protected ModelAndView onSubmit(
  		HttpServletRequest request,
  		HttpServletResponse response, 
  		Object command, 
  		BindException errors) throws Exception {
  	    	logger.info("\nSubmitting piano formativo progetto .... \n");    	    		  
  	    	
  	    	PercorsoFormativoCommand percorsoFormativoCommand = (PercorsoFormativoCommand) command;		
          	Map sessionParams = (HashMap) request.getSession().getAttribute("sessionParams");    	    
  				try{
  					FpapUser user = Utility.getUser(request);
  				Map descriptionProjectMap=((Map)((Map)request.getSession().getAttribute("sessionParams")).get("descriptionAssociativeMap"));
  				List listaDescriptionMap=null;
  				if(descriptionProjectMap!=null)
  				{
  					listaDescriptionMap=(List)descriptionProjectMap.get(Constants.TB_CSCH_COD_SCHEDA_VALUTAZIONE_FINALE);
  				}
  				List descriptionListForSave=getRequestDescritpionsList(request,String.valueOf(Constants.TB_CSCH_COD_SCHEDA_VALUTAZIONE_FINALE),percorsoFormativoCommand.getPercorsoFormativo());
  					
  				submitValutazioneFinaleP21BS.save(descriptionListForSave, listaDescriptionMap,percorsoFormativoCommand.getPercorsoFormativo(),user);
  				
  				
  				
  				
  				TbPefPercorsoformativoCl percorsoFormativo = percorsoFormativoService.findPercorsoFormativoByKey(percorsoFormativoCommand.getPercorsoFormativo().getId());
  		    	percorsoFormativoCommand.setPercorsoFormativo(percorsoFormativo);
  		    	
  				
  				WebUtils.setSessionAttribute(request, "sessionCommand", percorsoFormativoCommand);

  		    	//rimuovo la mappa delle descrizioni associate alla scheda
  		    	sessionParams.remove("descriptionAssociativeMap");


  		    	super.aggiornaTab(request, 
  	    				percorsoFormativo.getId().getPefProSeqProgettoFkPk(), 
  	    				percorsoFormativo.getId().getPefNumProgrpercorsoPk());
  				request.getSession().setAttribute("message", getMessageSourceAccessor().getMessage("progettoValutazione.success"));
  		        
  			} 
  			catch (Exception e) {			
  				logger.error("progettoValutazione->onSubmit",e);
  				request.setAttribute("error", getMessageSourceAccessor().getMessage("progettoValutazione.error"));
  				recuperaDescrizioniInCasoDiErrore(request, Constants.TB_CSCH_COD_SCHEDA_VALUTAZIONE_FINALE, percorsoFormativoCommand.getPercorsoFormativo());
  				return showForm(request, errors, getSuccessView());
  			}			


  			WebUtils.setSessionAttribute(request, "sessionParams", sessionParams);
  			ModelAndView modelAndView=new ModelAndView(getSuccessView(),"command",percorsoFormativoCommand);
  			return showForm(request, errors, modelAndView.getViewName(),modelAndView.getModel());
    }  
  
  
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    throws ServletException {
      binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));    
       
    }
  
  
}



