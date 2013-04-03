package com.linksmt.fpap.controllers.abstractController;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.linksmt.fpap.command.PercorsoFormativoCommand;
import com.linksmt.fpap.command.PianoFormativoCommand;
import com.linksmt.fpap.command.ProgettoCommand;
import com.linksmt.fpap.command.UnitaFormativaCommand;
import com.linksmt.fpap.controllers.ControllerConstants;
import com.linksmt.fpap.model.TbApdDesprogettoCa;
import com.linksmt.fpap.model.TbApdDesprogettoCaId;
import com.linksmt.fpap.model.TbDesDescrizioniCt;
import com.linksmt.fpap.model.TbDesDescrizioniCtId;
import com.linksmt.fpap.model.TbDinqDescrizioneinquadramentoCa;
import com.linksmt.fpap.model.TbDinqDescrizioneinquadramentoCaId;
import com.linksmt.fpap.model.TbDpfDescrizionepercorsoCa;
import com.linksmt.fpap.model.TbDpfDescrizionepercorsoCaId;
import com.linksmt.fpap.model.TbDpifoDespianoformativoCa;
import com.linksmt.fpap.model.TbDpifoDespianoformativoCaId;
import com.linksmt.fpap.model.TbDufDescrizioneunitaformCa;
import com.linksmt.fpap.model.TbDufDescrizioneunitaformCaId;
import com.linksmt.fpap.model.TbPefPercorsoformativoCl;
import com.linksmt.fpap.model.TbPifoPianoformativoCl;
import com.linksmt.fpap.model.TbProProgettoCl;
import com.linksmt.fpap.model.TbScszSchedasezioneCt;
import com.linksmt.fpap.model.TbUfUnitaformativaCl;
import com.linksmt.fpap.services.TbDesDescrizioniCtService;
import com.linksmt.fpap.services.TbScszSchedasezioneCtService;

public abstract class submitAbstractDescrizioneController extends SimpleFormController implements ControllerConstants {

  private TbDesDescrizioniCtService desDescrizioneCtService;
  private TbScszSchedasezioneCtService tbScszSchedasezioneCtService=null;

  public void setTbScszSchedasezioneCtService(
  		TbScszSchedasezioneCtService tbScszSchedasezioneCtService) {
  	this.tbScszSchedasezioneCtService = tbScszSchedasezioneCtService;
  }


  public void setDesDescrizioneCtService(
  		TbDesDescrizioniCtService desDescrizioneCtService) {
  	this.desDescrizioneCtService = desDescrizioneCtService;
  }

  
  /**
   * Carica la mappa delle descrizioni presenti nella tipologica
   * 
   * @param request
   * */
  protected void caricaMappaDescrizioni(HttpServletRequest request,int value, Object object) throws Exception
  {
  	Map sessionParams = new HashMap();
  	try
  	{
  		sessionParams = (HashMap) request.getSession().getAttribute(
  				"sessionParams");
  
  		if (sessionParams != null
  				&& sessionParams.get("descriptionMap") == null) {
  			// RecuperO dalla base date le descrizioni(tipologie) predefinite
  			List<TbDesDescrizioniCt> listaDescrizioni = desDescrizioneCtService.getAllDescrizioni();
  			Map descriptionMap = creaMappa(listaDescrizioni);
  			sessionParams.put("descriptionMap", descriptionMap);
  
  		}
  
  		if (sessionParams != null
  				&& sessionParams.get("descriptionAssociativeMap") == null) {
  			List listaDescrizioniProgetto=null;
  			// RecuperO dalla base dati le descrizioni associate al progetto
  			if (object instanceof ProgettoCommand) {
  				ProgettoCommand progettoCommand = (ProgettoCommand)object;
  				listaDescrizioniProgetto = new ArrayList<TbApdDesprogettoCa>();

  				listaDescrizioniProgetto.addAll(progettoCommand.getProgetto()
  						.getTbApdDesprogettoCas());
  			} else if (object instanceof PercorsoFormativoCommand)// a seconda
  																	// del tipo
  																	// command
  																	// recupero
  																	// le
  																	// descrizioni
  			{
  
  				PercorsoFormativoCommand percorsoFormativoCommand = (PercorsoFormativoCommand) WebUtils
  						.getSessionAttribute(request, "sessionCommand");
  				listaDescrizioniProgetto = new ArrayList();
  				listaDescrizioniProgetto.addAll(percorsoFormativoCommand
  						.getPercorsoFormativo()
  						.getTbDpfDescrizionepercorsoCas());
  				listaDescrizioniProgetto.addAll(percorsoFormativoCommand
  						.getPercorsoFormativo()
  						.getTbDinqDescrizioneinquadramentoCas());
  			} else if (object instanceof UnitaFormativaCommand)// a seconda del
  																// tipo command
  																// recupero le
  																// descrizioni
  			{
  
  				UnitaFormativaCommand unitaFormativaCommand = (UnitaFormativaCommand) WebUtils
  						.getSessionAttribute(request, "sessionCommand");
  				listaDescrizioniProgetto = new ArrayList<TbDpfDescrizionepercorsoCa>();
  				listaDescrizioniProgetto.addAll(unitaFormativaCommand
  						.getUnitaFormativa().getTbDufDescrizioneunitaformCas());
  			}
  			else if (object instanceof PianoFormativoCommand)// a seconda del
  				// tipo command
  				// recupero le
  				// descrizioni
  			{
  			
  				PianoFormativoCommand pianoFormativoCommand = (PianoFormativoCommand) WebUtils
  				.getSessionAttribute(request, "sessionCommand");
  				listaDescrizioniProgetto = new ArrayList<TbDpifoDespianoformativoCa>();
  				listaDescrizioniProgetto.addAll(pianoFormativoCommand
  				.getTbPifoPianoformativoCl().getTbDpifoDespianoformativoCas());
  			}

  			Map descriptionAssociativeMap = creaMappa(listaDescrizioniProgetto);
  			sessionParams.put("descriptionAssociativeMap",
  					descriptionAssociativeMap);
  			//Recuper codifica scheda
  			if(tbScszSchedasezioneCtService!=null)
  			{
  				List<TbScszSchedasezioneCt> tbScszSchedasezioneCts=tbScszSchedasezioneCtService.getTbScszSchedasezioneCtPerCodiceScheda(value);
  				sessionParams.put("tbScszSchedasezioneCts",
  						tbScszSchedasezioneCts);
  			}
  		}
  		WebUtils.setSessionAttribute(request, "sessionParams", sessionParams);
  		request.setAttribute("keyVal", value);
  	}
  	catch (Exception e) {
  		logger.error("",e);
  		throw e;
  	}
  }


  // creazione mappa descrizioni
  private Map creaMappa(List listaDescrizioni) {
  	Map<Integer, List<Object>> generalMap = new HashMap<Integer, List<Object>>();
  	int key = 0;
  	if (listaDescrizioni != null) {
  		Iterator<Object> itListaDescrizioni = listaDescrizioni.iterator();
  		while (itListaDescrizioni.hasNext()) {
  			Object descrizioniCt = itListaDescrizioni.next();

  			if (descrizioniCt instanceof TbDesDescrizioniCt) {
  				key = ((TbDesDescrizioniCt) descrizioniCt).getId()
  						.getDesCschCodNumeroFkPk();
  			} else if (descrizioniCt instanceof TbApdDesprogettoCa) {
  				key = ((TbApdDesprogettoCa) descrizioniCt).getId()
  						.getApdDesCodificaschedaFkPk();
  			} else if (descrizioniCt instanceof TbDpfDescrizionepercorsoCa) {
  				key = ((TbDpfDescrizionepercorsoCa) descrizioniCt).getId()
  						.getDpfDesCschCodificaschedaFkPk();
  			} else if (descrizioniCt instanceof TbDufDescrizioneunitaformCa) {
  				key = ((TbDufDescrizioneunitaformCa) descrizioniCt).getId()
  						.getDufDesCschCodificaschedaFkPk();
  			} else if (descrizioniCt instanceof TbDinqDescrizioneinquadramentoCa) {
  				key = ((TbDinqDescrizioneinquadramentoCa) descrizioniCt)
  						.getId().getDinqDesCschCodificaschedaFkPk();
  			}else if (descrizioniCt instanceof TbDpifoDespianoformativoCa) {
  				key = ((TbDpifoDespianoformativoCa) descrizioniCt)
  				.getId().getDpifoDesCodificaschedaFkPk();
  	}
  			// Recupero lista descrizioni nella mappa
  			List<Object> listaDescrizioniMap = (List<Object>) generalMap
  					.get(key);
  			if (listaDescrizioniMap == null) {
  				listaDescrizioniMap = new ArrayList<Object>();

  			}
  			// aggiungo alla lista la nuova descrizione
  			listaDescrizioniMap.add(descrizioniCt);
  			generalMap.put(key, listaDescrizioniMap);

  		}
  	}
  	return generalMap;
  }

  protected List getRequestDescritpionsList(HttpServletRequest request,
  		String codScheda, Object object) throws Exception {
  	List requestDescritpionsList = new ArrayList();
  	Map descriptionProjectMap = ((Map) ((Map) request.getSession()
  			.getAttribute("sessionParams"))
  			.get("descriptionAssociativeMap"));

  	// cerco tra i tipi unita selezionati nelle chekboxes
  	Enumeration enumer = request.getParameterNames();
  	String currParamName = null;
  	while (enumer.hasMoreElements()) {
  		currParamName = (String) enumer.nextElement();
  		if (currParamName.indexOf(codScheda) == 0) {
  			Object ritorno = null;
  			/* Recupero la descrizione */
  			request.getParameter("currParamName");

  			TbDesDescrizioniCt tbDesDescrizioniCt=new TbDesDescrizioniCt();
  			TbDesDescrizioniCtId id=null;
  			if(!(object instanceof TbDinqDescrizioneinquadramentoCa))
  			{
  				id=new TbDesDescrizioniCtId(
  						Integer
  						.parseInt(currParamName.substring(codScheda
  								.length())),
  								Integer
  								.parseInt(codScheda));
  			}
  			else
  			{
  				id=new TbDesDescrizioniCtId(
  						Integer.parseInt(currParamName.substring(
  								codScheda.length(), currParamName
  								.indexOf("_"))),
  								Integer
  								.parseInt(codScheda));
  			}
  			tbDesDescrizioniCt.setId(id);
  			
  			/* inserimento descrizione progetto */
  			if (object instanceof TbProProgettoCl) {
  				TbProProgettoCl tbProProgettoCl = (TbProProgettoCl) object;

  				TbApdDesprogettoCaId apdDesprogettoCaId = new TbApdDesprogettoCaId();
  				apdDesprogettoCaId.setApdDesCodificaschedaFkPk(Integer
  						.parseInt(codScheda));
  				apdDesprogettoCaId.setApdDesSeqDescrizioniFkPk(Integer
  						.parseInt(currParamName.substring(codScheda
  								.length())));
  				apdDesprogettoCaId.setApdProSeqProgettoFkPk(tbProProgettoCl
  						.getProSeqProgettoPk());
  				TbApdDesprogettoCa apdDesprogettoCa = new TbApdDesprogettoCa();
  				apdDesprogettoCa.setId(apdDesprogettoCaId);
  				apdDesprogettoCa.setApdDescTestolibero(request.getParameter(currParamName));
  				apdDesprogettoCa.setTbDesDescrizioniCt(tbDesDescrizioniCt);
  				
  				ritorno = apdDesprogettoCa;
  			}
  			/* inserimento descrizione ruoli */
  			else if (object instanceof TbDinqDescrizioneinquadramentoCa) {
  				
  				TbDinqDescrizioneinquadramentoCaId tbDinqDescrizioneinquadramentoCaId= new TbDinqDescrizioneinquadramentoCaId();
  				tbDinqDescrizioneinquadramentoCaId.setDinqDesCschCodificaschedaFkPk(Integer.parseInt(codScheda));
  				tbDinqDescrizioneinquadramentoCaId.setDinqDesDescrizioneSeqFkPk(Integer.parseInt(currParamName.substring(
  						codScheda.length(), currParamName
  						.indexOf("_"))));
  				tbDinqDescrizioneinquadramentoCaId.setDinqPefNumProgettoSeqFkPk(((TbDinqDescrizioneinquadramentoCa)object).getId()
  						.getDinqPefNumProgettoSeqFkPk());
  				tbDinqDescrizioneinquadramentoCaId.setDinqPefNumPercorsoSeqFkPk(((TbDinqDescrizioneinquadramentoCa)object).getId()
  						.getDinqPefNumPercorsoSeqFkPk());
  				tbDinqDescrizioneinquadramentoCaId.setDinqTinqCodTipoinquadramentoPk(Integer.parseInt(currParamName.substring(
  						currParamName.indexOf("_") + 1,
  						currParamName.length())));
  				TbDinqDescrizioneinquadramentoCa tbDinqDescrizioneinquadramentoCa=new TbDinqDescrizioneinquadramentoCa();
  				tbDinqDescrizioneinquadramentoCa.setId(tbDinqDescrizioneinquadramentoCaId);
  				tbDinqDescrizioneinquadramentoCa.setDinqDescTestolibero(request.getParameter(currParamName));

  				tbDinqDescrizioneinquadramentoCa.setTbDesDescrizioniCt(tbDesDescrizioniCt);
  				
  				ritorno = tbDinqDescrizioneinquadramentoCa;
  			}

  			/* inserimento descrizione percorso */
  			else if (object instanceof TbPefPercorsoformativoCl) {
  				TbPefPercorsoformativoCl tbPefPercorsoformativoCl = (TbPefPercorsoformativoCl) object;
  				
  				/*Seto la descrizione nell'oggetto TbApd*/
  				TbDpfDescrizionepercorsoCaId tbDpfDescrizionepercorsoCaId= new TbDpfDescrizionepercorsoCaId();
  				tbDpfDescrizionepercorsoCaId.setDpfDesCschCodificaschedaFkPk(Integer.parseInt(codScheda));
  				tbDpfDescrizionepercorsoCaId.setDpfDesDescrizioneSeqFkPk(Integer.parseInt(currParamName
  						.substring(codScheda.length())));
  				tbDpfDescrizionepercorsoCaId.setDpfPefNumProgettoSeqFkPk(tbPefPercorsoformativoCl.getId()
  						.getPefProSeqProgettoFkPk());
  				tbDpfDescrizionepercorsoCaId.setDpfPefNumPercorsoSeqFkPk(tbPefPercorsoformativoCl.getId()
  						.getPefNumProgrpercorsoPk());
  				TbDpfDescrizionepercorsoCa tbDpfDescrizionepercorsoCa=new TbDpfDescrizionepercorsoCa();
  				tbDpfDescrizionepercorsoCa.setId(tbDpfDescrizionepercorsoCaId);
  				tbDpfDescrizionepercorsoCa.setDpfDescTestolibero(request.getParameter(currParamName));
  				
  				tbDpfDescrizionepercorsoCa.setTbDesDescrizioniCt(tbDesDescrizioniCt);
  				
  				ritorno = tbDpfDescrizionepercorsoCa;
  				
  			}
  			/* inserimento descrizione unita formativa */
  			else if (object instanceof TbUfUnitaformativaCl) {
  				TbUfUnitaformativaCl tbUfUnitaformativaCl = (TbUfUnitaformativaCl) object;
  				
  				TbDufDescrizioneunitaformCaId tbDufDescrizioneunitaformCaId= new TbDufDescrizioneunitaformCaId();
  				tbDufDescrizioneunitaformCaId.setDufDesCschCodificaschedaFkPk(Integer.parseInt(codScheda));
  				tbDufDescrizioneunitaformCaId.setDufDesDescrizioneSeqFkPk(Integer.parseInt(currParamName
  						.substring(codScheda.length())));
  				tbDufDescrizioneunitaformCaId.setDufUfNumProgettoSeqFkPk(tbUfUnitaformativaCl.getId()
  						.getUfPefSeqProgettoFkPk());
  				tbDufDescrizioneunitaformCaId.setDufUfNumPercorsoSeqFkPk(tbUfUnitaformativaCl.getId()
  						.getUfPefNumPercorsoFkPk());
  				tbDufDescrizioneunitaformCaId.setDufUfNumUnitacompetenzaFkPk(tbUfUnitaformativaCl.getId()
  						.getUfNumUnitaformativaPk());
  				TbDufDescrizioneunitaformCa tbDufDescrizioneunitaformCa=new TbDufDescrizioneunitaformCa();
  				tbDufDescrizioneunitaformCa.setId(tbDufDescrizioneunitaformCaId);
  				tbDufDescrizioneunitaformCa.setDufDescTestolibero(request.getParameter(currParamName));

  				tbDufDescrizioneunitaformCa.setTbDesDescrizioniCt(tbDesDescrizioniCt);
  				
  				ritorno = tbDufDescrizioneunitaformCa;
  			}
  			/* inserimento descrizione piano formativo */
  			else if (object instanceof TbPifoPianoformativoCl) {
  				TbPifoPianoformativoCl tbPifoPianoformativoCl = (TbPifoPianoformativoCl) object;
  				
  				TbDpifoDespianoformativoCaId tbDpifoDespianoformativoCaId= new TbDpifoDespianoformativoCaId();
  				tbDpifoDespianoformativoCaId.setDpifoDesCodificaschedaFkPk(Integer.parseInt(codScheda));
  				tbDpifoDespianoformativoCaId.setDpifoDesSeqDescrizioniFkPk(Integer.parseInt(currParamName
  						.substring(codScheda.length())));
  				tbDpifoDespianoformativoCaId.setDpifoPifoCodPianoformativoFkPk(tbPifoPianoformativoCl.getPifoCodPianoformativoPk());
  				
  				TbDpifoDespianoformativoCa tbDpifoDespianoformativoCa=new TbDpifoDespianoformativoCa();
  				tbDpifoDespianoformativoCa.setId(tbDpifoDespianoformativoCaId);
  				tbDpifoDespianoformativoCa.setDpifoDescTestolibero(request.getParameter(currParamName));

  				tbDpifoDespianoformativoCa.setTbDesDescrizioniCt(tbDesDescrizioniCt);
  				
  				ritorno = tbDpifoDespianoformativoCa;
  			}
  			if (ritorno != null) {
  				requestDescritpionsList.add(ritorno);
  				// listaDescrizioniObject.add(ritorno);
  			}
  		}
  	}
  	return requestDescritpionsList;
  }
  
  protected void recuperaDescrizioniInCasoDiErrore(HttpServletRequest request, int idScheda, Object object) throws Exception {
  	Map sessionParams = (HashMap) request.getSession().getAttribute("sessionParams");
  	Map descriptionAssociativeMap = (Map) sessionParams.get("descriptionAssociativeMap");
  	if(descriptionAssociativeMap == null)
  		descriptionAssociativeMap = new HashMap();
  	List descritpionsList = getRequestDescritpionsList(request,String.valueOf(idScheda), object);
  	descriptionAssociativeMap.put(idScheda, descritpionsList);
  	sessionParams.put("descriptionAssociativeMap", descriptionAssociativeMap);
  	WebUtils.setSessionAttribute(request, "sessionParams", sessionParams);
  	request.setAttribute("keyVal", idScheda);
  }
  
  protected void logError(String methodName, Throwable e) {
  	logger.error(new StringBuilder(this.getClass().getSimpleName()).append("->").append(methodName).toString(), e);
  }

}
