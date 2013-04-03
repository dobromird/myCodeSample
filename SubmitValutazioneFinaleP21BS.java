package com.linksmt.fpap.businessService.acquisizione.percorsoformativo;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

import com.linksmt.fpap.authentication.FpapUser;
import com.linksmt.fpap.businessService.abstractBS.SubmitAbstractPercorsoFormativoBS;
import com.linksmt.fpap.model.TbPefPercorsoformativoCl;
import com.linksmt.fpap.utils.Constants;

public class SubmitValutazioneFinaleP21BS extends SubmitAbstractPercorsoFormativoBS {
  
   protected final Log logger = LogFactory.getLog(getClass());
   public void save(
  		List descriptionListForSave,
  		List listaDescriptionMap,
  		TbPefPercorsoformativoCl tbPefPercorsoformativoCl,
  		FpapUser user
  		) 
{
     
  	    	logger.info("\n Business Submitting valutazione percorso .... \n");    	    		  
  	    	    	    
  			try{
  			    super.inserisciDescrizioni(listaDescriptionMap,descriptionListForSave);

  			    super.verificaComplettamento(Constants.TB_CSCH_COD_SCHEDA_VALUTAZIONE_FINALE,
  			    		tbPefPercorsoformativoCl.getId().getPefProSeqProgettoFkPk(),
  			    		tbPefPercorsoformativoCl.getId().getPefNumProgrpercorsoPk(),
  			    		null,
  			    		descriptionListForSave, null);
  			    
  			    clear();
  			}
  			catch (Exception e) {			
  				logger.error("SubmitValutazioneFinaleP21BS->save",e);
  				throw new HibernateException(e);
  			}					
   }
  
  
}
