<%@ page language = "java" contentType="text/html; charset=utf-8" %>
<!-- /views/acquisizione/gestioneValutazioneFinale_p21.jsp -->

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="str" uri="http://jakarta.apache.org/taglibs/string-1.1" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@page import="com.linksmt.fpap.model.TbDesDescrizioniCt"%><script type="text/javascript">
jQuery(document).ready(function(){
  observeFormData("#form8");	// questa istruz. serve a far CONTROLLARE a jquery.tabs.js se la form e' stata salvata
  disableForm();
   jQuery("#btnSalva8").click(function(){
     	formDataSaved();	// questa istruz. serve a COMUNICARE a jquery.tabs.js che la form e' stata salvata

     	submitForm("#form8","gestioneValutazioneFinale_p21.htm","#box8", 10);      
        return false;
    }); 

  
  	// questa istruz. serve a far CONTROLLARE il numero massimo di caratteri da inserire nelle textarea
   $('div.box textarea').maxlengthText(3500);  
  
});
</script>
<div id="box8" class="box">
  <table class="bkg" width="100%"> 
    <tr>
    	<td align="left">
    		<img style="cursor: pointer;" src="images/pdfPrinter.gif" id="idPrint" title="stampa in pdf" onclick="creaPdf('box8');"/>
    	</td>
    </tr>   	

    <tr>
    	<td class="titolo" align="left" valign="top">Q)Valutazione finale del percorso formativo</td>
    </tr>
  		
  	<tr>
      	<td class="bkg" valign="top">
  			<table class="bkg">
  				<tbody>
  						<c:if test="${not empty messaggioCtrl}">
  							<tr>
  								<td>
  									<font color="red" id="messaggioCtrl"><c:out value="${messaggioCtrl}" /></font>
  									<c:remove var="messaggioCtrl"/>
  								</td>
  							</tr>
  						</c:if>
  						
  						<c:if test="${not empty message}">
  							<c:if test="${empty i}">
  								<c:set var="i" value="1" scope="session"></c:set>
  							</c:if>
  							 <tr>
  							  	<td valign="top" class="label">
  									<font color="green">
  										<font color="green"><c:out value="${message}" /></font>
  										<c:if test="${not empty i && i>0}" >
  											<c:if test="${not empty i && i==2}" >
  												<c:remove var="message" scope="session"/>
  												<c:remove var="i" scope="session"/>
  											</c:if>
  											<c:if test="${not empty i && i==1}" >
  												<c:set var="i" value="2" scope="session"></c:set>
  												<c:set value="${message}" var="message" scope="session"/>
  											</c:if>
  										</c:if>
  									</font>
  								</td>
  							 </tr> 
  						</c:if>
  					<tr>
  						<td>
  							<form name="tab_PR_p22" action="gestioneValutazioneFinale_p21.htm" method="POST" enctype="multipart/form-data" id="form8">					
  								<table width="100%">
  									<tbody>
  										<tr>
  											<td colspan ="2">
  												<fieldset>
  													<!-- <legend class="titoloarangio">Q)Valutazione finale del percorso formativo</legend> -->
  													<table width="100%">
  													<c:set var="i" value="1" scope="page" />
  														<%
  															int key=(Integer)request.getAttribute("keyVal");
  															Map description=(Map)((Map)request.getSession().getAttribute("sessionParams")).get("descriptionMap");
  															List pageList =(List)description.get(key);
  															request.setAttribute("pageList",pageList);
  															Map descriptionProject=(Map)((Map)request.getSession().getAttribute("sessionParams")).get("descriptionAssociativeMap");
  															List arrayLista=null;
  															if(descriptionProject!=null)
  															{
  																arrayLista=(List)descriptionProject.get(key);
  															}
  															request.setAttribute("listaDescrizioniPagina",arrayLista);
  														%>
  														<tbody>
  																<c:forEach items="${pageList}" var="itemDesc">
  																	<c:if test="${not empty itemDesc.desScszSeqSezioneFk && itemDesc.desScszSeqSezioneFk==i}">
  																			<tr>
  																				<td align="left" class="label-strong">
  																					<c:out value="${itemDesc.desDescDescrizione}"/>
  																				</td>
  																			</tr>
  																			<tr>
  																				<td align="left" class="labelNote">
  																					<c:if test="${(not empty itemDesc.desDescNota) && not(itemDesc.desDescNota eq '')&& not(itemDesc.desDescNota eq ' ')}">
  																						<c:out value="${itemDesc.desDescNota}"/>
  																						<br/>
  																					</c:if>
  																					<textarea rows="4" cols="90" name="<c:out value='${itemDesc.id.desCschCodNumeroFkPk}'/><c:out value='${itemDesc.id.desSeqDescrizionePk}'/>" id="<c:out value='${itemDesc.id.desSeqDescrizionePk}'/>"><c:forEach items="${listaDescrizioniPagina}" var="itemLista"><c:if test="${itemLista.tbDesDescrizioniCt.id.desSeqDescrizionePk == itemDesc.id.desSeqDescrizionePk}"><c:out value="${itemLista.dpfDescTestolibero}"/></c:if></c:forEach></textarea>
  																				</td>
  																			</tr>
  																	</c:if>
  																</c:forEach>
  																<c:set var="i" value="${i+1}" scope="page" />
  														</tbody>
  													</table>
  												</fieldset>
  											</td>
  										</tr>			
  										<tr>
  											<td colspan="1" align="center">
  												<div id="displayBtnForSubmit" style="display:<c:out value='${sessionParams.modifiedNotAllowed}'/>">
  													<input class="btnsubmit" value="Salva" type="button" id="btnSalva8">
  													&nbsp;
  													<input class="btnsubmit" value="Resetta campi" type="button" onclick="resettaCampi(this.form);">
  												</div>
  											</td>
  										</tr>

  										<tr>
  											<td colspan="1" align="left" nowrap="nowrap">
  												<table class="noprint">
  													<tbody>
  														<tr>
  															<td>
  																<a class="button" href="#" onclick="location.href='progettoEdit.htm?codPro=<c:out value="${command.percorsoFormativo.id.pefProSeqProgettoFkPk}"/>&&visualizzaDettaglio=1&&selectedTab=5';" ><span>&lt;&lt;</span></a>
  															</td>
  															<td nowrap="nowrap">
  																<a id="torna-indietro" class="param-short" href="#" onclick="location.href='progettoEdit.htm?codPro=<c:out value="${command.percorsoFormativo.id.pefProSeqProgettoFkPk}"/>&&visualizzaDettaglio=1&&selectedTab=5';" >Torna indietro</a>
  															</td>
  														</tr>
  													</tbody>
  												</table>
  											</td>
  										</tr>

  									</tbody>
  								</table>
  							</form>
  						</td>
  					</tr>
  				</tbody>
  			</table>
  		</td>
  	</tr>
  </table>	
</div>  
