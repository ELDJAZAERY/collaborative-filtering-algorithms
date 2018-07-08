<!DOCTYPE html>
<html class="nojs html css_verticalspacer" lang="fr-FR">
 <head>

  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta name="description" content="outils d'evaluation">
  <meta name="keywords" content="filtrage collaboratif , filtrage d'infos , filtrage d'information , collaboratif , collaboratif statique ">
  <meta name="author" content="EL-DJAZAERY AZZOUZ TEAM">
     
  <link rel="shortcut icon" href="imgs/icon.jpg">
  <title>F Collaboratif</title>
     
  <!-- CSS -->
  <link rel="stylesheet" type="text/css" href="css/site_global.css?crc=443350757"/>
  <link rel="stylesheet" type="text/css" href="css/master_fi_app.css?crc=3979489964"/>
  <link rel="stylesheet" type="text/css" href="css/index.css?crc=191885772" id="pagesheet"/>
  <link rel="stylesheet" type="text/css" href="css/FI_App_style.css"/>
  <link rel="stylesheet" href="css/W3.CSS">
  <link rel="stylesheet" href="css/font-awesome.min.CSS">
  <link rel="stylesheet" href="css/loading_annimation.css">

  <!-- Script -->
  <script src="scripts/Chart.js"></script>
  <script src="scripts/jQuery.js"></script>
  <script src="scripts/underscore.js"></script>
  <script src="scripts/jspdf.min.js"></script>
  <script src="scripts/html2canvas.js"></script>
       
 </head>
 <body class="noselect">
     
 <div id="couche" class="couche_desavtiver inv">
<div id="loading_bar" class="cs-loader inv">
  <div class="cs-loader-inner">
    <label>	&#9679;</label>
    <label>	&#9679;</label>
    <label>	&#9679;</label>
    <label>	&#9679;</label>
    <label>	&#9679;</label>
    <label>	&#9679;</label>
  </div>
  <div id="footer_loading">Veuillez patienter...</div>    
</div>
</div>

     
  <div class="clearfix borderbox noselect" id="page"><!-- column -->
   <div class="browser_width colelem" id="u94-bw">
    <div id="u94"><!-- group -->
     <div class="clearfix" id="u94_align_to_page">
      <div class="clearfix grpelem" id="u103-4"><!-- content -->
       <p>Filtrage&nbsp; Collaboratif</p>
      </div>
     </div>
    </div>
   </div>
   <div class="PamphletWidget clearfix colelem" id="pamphletu581"><!-- none box -->
    <div class="ThumbGroup clearfix grpelem" id="u598"><!-- none box -->
     <div class="popup_anchor" id="u603popup">
      <div class="Thumb popup_element shadow clearfix" id="u603"><!-- group -->
       <div class="clearfix grpelem" id="u604-4"><!-- content -->
        <p>Outil D'&eacute;valuation</p>
       </div>
      </div>
     </div>
     <div class="popup_anchor" id="u599popup">
      <div class="Thumb popup_element clearfix" id="u599"><!-- group -->
       <div class="clearfix grpelem" id="u600-4"><!-- content -->
        <p > Outil D'affichage </p>
       </div>
      </div>
     </div>
    </div>
    <div class="popup_anchor" id="u582popup">
        
    <main>

     <form action="valide" method="post" enctype="multipart/form-data">
         
        <div class="inv"> 
            <input id="L_S" name="List_S" value="" />
            <input id="L_E" name="List_E" value="" />
            <input id="L_K" name="List_K" value="" />
            <input id="L_C" name="List_C" value="" /> 
            <input id="page_acctuel" name="page_acctuel" value="Base" />
        </div>
                  
 <div id="slid" class="slidebar">
        <p class="titre"> Panneau de configuration </p> 
   
<!-- Menu horizontal droite --> 
<input type="checkbox" id="menuToggle">
<label id="lbel_menu" for="menuToggle" class="menu-icon">&#9776;</label>
<div id="bar_gauche"> 
<div id="header_menu"></div>
<div id="menu-accordeon">     
<ul>
   <li onclick="M1();"><a>Base de donn&eacute;es</a></li>
    <li><a>Filtrage Collaboratif</a>
      <ul>
         <li onclick="M2_1();"><a>FC Mono-crit&egrave;re</a></li>
         <li onclick="M2_2();"><a>FC Multi-crit&egrave;re</a></li>
      </ul>
   </li>
   <li><a>FC Classification</a>
      <ul>
         <li onclick="M3_1();"><a>FC avec K-Mean</a></li>
         <li onclick="M3_2();"><a>FC avec KNN</a></li>
      </ul>
   </li>
   <li onclick="M4();"><a>Classification & optimisation</a></li>
</ul>
</div>
</div> 

    <div id="fc_type_conf" class="inv">
        <label>Crit&egrave;res &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</label>
        <input name="priorite1" id="priorite1" type="number" step="0.1" min="0" max="1" placeholder="crit&egrave;re 1">
        <input name="priorite2" id="priorite2" type="number" step="0.1" min="0" max="1" placeholder="crit&egrave;re 2">
        <input name="priorite3" id="priorite3" type="number" step="0.1" min="0" max="1" placeholder="crit&egrave;re 3">
        <input name="priorite4" id="priorite4" type="number" step="0.1" min="0" max="1" placeholder="crit&egrave;re 4">
    </div>

     <div id="fc_classif_Kmean" class="inv">
         <div id="nb_part" class="Ginp2"> 
             <label>Nb de Partitions &nbsp;&nbsp;:</label> 
             <input type="number"  name="NB_Part" placeholder="nombre de Partition" min="2" value="5" required/>
         </div>
     </div>

     <div id="fc_classif_KNN" class="inv">
         <div class="Ginp1"> 
             <label>Nb de Voisins &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</label>
             <input type="number" name="NB_Voisins" placeholder="nombre de Voisins" min="2" value="5" required/>
         </div>     
     </div>
     
     <div id="fc_classif_opt" class="inv">
         <div class="Ginp2">   
             <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Nombre d'iteration &nbsp;&nbsp;:</label> 
             <input type="number" name="nbIter" placeholder="Nombre d'iteration" min="10" value="100" required/>&nbsp;&nbsp;&nbsp;&nbsp;
             <label>pheromone initiale &nbsp;&nbsp;:</label> 
             <input type="number" name="pheromone0" placeholder="pheromone initiale" min="0" max="1" value="0.1" step="0.1" required/>
         </div>
         <div class="Ginp1"> 
             <label>alpha &nbsp;&nbsp;&nbsp;&nbsp;:</label>
             <input type="number" name="alpha" placeholder="alpha" min="0" max="1" step="0.1" value="0.5" required/>&nbsp;&nbsp;
             <label>beta &nbsp;&nbsp;&nbsp;&nbsp;:</label>
             <input type="number" name="beta" placeholder="beta" min="0" max="1" step="0.1" value="0.4" required/>&nbsp;&nbsp;
             <label>q0 &nbsp;&nbsp;&nbsp;:</label>
             <input type="number" name="q0" placeholder="q0" min="0" max="1" step="0.1" value="0.6" required/>&nbsp;&nbsp;
             <label>p &nbsp;&nbsp;&nbsp;:</label>
             <input type="number" name="p" placeholder="p" min="0" max="1" step="0.1" value="0.3" required/>&nbsp;&nbsp;
         </div> 
         <input type="submit" class="w3-btn w3-ripple w3-grey w3-text-amber-2 w3-round-xxlarge bt3" value="Lancer" onclick="loading();"/>&nbsp;&nbsp;
     </div>
     
    <div id="base_conf" >
        <div id="section1">
          <div id="dexc_movielens" >
              <p class="pp1"> - Nous Mettons a votre disposition une base de donn&eacute;es MovieLens  &nbsp; </p>
              <p class="pp2"> * base de 100K d'&eacute;valuations  </p>
              <p class="pp3"> * Mono Crit&egrave;re </p>
          </div>
          <div id="desc_base_param">    
              <p class="pp1"> - Si vous voulez  utiliser votre propre Base de donn&eacute;es alors veuillez selectionner : &nbsp;</p>
              <p class="pp2"> * 3 Fichiers '.xls' '.xlsx' ou '.CSV'  le 1er fichier contient  les 'Users', </p>
              <p class="pp3"> &nbsp;&nbsp;&nbsp;&nbsp; le 2&egrave;me les 'Items' et le 3&egrave;me celui des &eacute;valuations 'Rating' </p>
          </div>
        </div>
        <div id="choise_file">
     	   <input type="file" name="file" id="file" class="inputfile inputfile-2 inv" data-multiple-caption="{count} files selected" multiple />
		   <label for="file"><span>Choose a file&hellip;</span></label>
        </div>
    </div>  
     
     
    <div id="fc_conf" class="inv">
        <div id="part1">
          <div id="Ginps_seuils">
                <div id="Ginp1" class="Ginp1 fieldset">
                   <label>Echantillons :</label>
                   <input type="number" id="ech1" placeholder="N 1" min="1" value="1000" onchange="sauvgarde();" required/>
                   <p id="Supp_E" class="w3-btn-floating w3-red w3-disabled bouton_supp" onclick="supprimer_echant();">-</p>
                   <p id="Add_E" class="w3-btn-floating w3-teal bouton_ajout" onclick="ajouter_echant();">+</p>                                  
                </div>
                <div id="Ginp2" class="Ginp2"> 
                   <label>Seuil &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</label>
                   <input type="number" id="seuil_fix" placeholder="Seuil" step="0.1" min="-1" max="1" value="0.0" onchange="sauvgarde();" required/>
                </div>
            </div>
            
            <div id="Ginps_Echants">
                <div id="Ginp12" class="Ginp1">
                   <label>Echantillons :</label>
                   <input type="number" id="echant_fix"  placeholder="N Users"  min="1" value="1000" onchange="sauvgarde();" required/>
                </div>
                <div id="Ginp11" class="Ginp2 fieldset"> 
                   <label>Seuils &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</label>
                   <input type="number" id="in1" placeholder="Seuil 1" step="0.1" min="0" max="1" value="0.5" onchange="sauvgarde();" required />
                   <p id="Supp_S" class="w3-btn-floating w3-red w3-disabled bouton_supp" onclick="supprimer_seuil();" >-</p>
                   <p id="Add_S" class="w3-btn-floating w3-teal bouton_ajout" onclick="ajouter_seuil();">+</p>              
                </div>
            </div>
            <div id="Ginps_KNN" class="inv">
               <div id="KNN-G1" class="Ginp1">
                 <label>Echantillons :</label>
                 <input type="number" id="ech_KNN" placeholder="N Users"  min="1" value="1000" onchange="sauvgarde();" required/>                
               </div>
               <div id="KNN-G2" class="Ginp2 fieldset">
                 <label>plus proche voisin :</label>
                 <input type="number"  id="K1" placeholder="K 1"  min="1" value="50" onchange="sauvgarde();" required/>                
                 <p id="supp_KNN" class="w3-btn-floating w3-red w3-disabled bouton_supp" onclick="supp_KNN();">-</p>
                 <p id="add_KNN" class="w3-btn-floating w3-teal bouton_ajout" onclick="add_KNN();">+</p>              
               </div>
            </div>
        </div>
        
             <input type="submit" class="w3-btn w3-ripple w3-grey w3-text-amber-2 w3-round-xxlarge bt3" value="Lancer" onclick="loading();"/>
        
      <div id="part2"> 
        <fieldset id="fielset_sim"> 
         <legend>Similarit&eacute;</legend>
         <div id="check1" class="chek">
             <p><input type="checkbox" id="COS" name="checkCOS" onchange="doAlert(this)"/><label for="COS"><span class="ui"></span>COS</label></p>
         </div>
         <div id="check2" class="chek">
             <p><input type="checkbox" id="PER" name="checkPER"/><label for="PER"><span class="ui"></span>PEARSON</label></p>
         </div>
        </fieldset>    
      </div>
        
       <div id="part3">
           <select id="Iu" name="fc_type" onchange="dis_sim();">
              <option value="User Based">User Based</option>
              <option value="Item Based">Item Based</option>
              <option value="slope One">Slope One</option>
            </select>
          
            <select id="Es" name="fc_form_type" onchange="synch_graph()">
              <option value="Echantillonnage">Echantillonnage</option>
              <option value="Seuil">Seuil</option>
              <option value="KNN">K-NN</option>
            </select> 
      </div>
        
    </div>
 </div> 
        
        
        
     <div class="ContainerGroup clearfix" id="u582"><!-- stack box -->
      <div class="Container clearfix grpelem" id="u583"><!-- column -->
       <div class="clearfix colelem" id="pu825"><!-- group -->
        <div class="grpelem" id="u825"><!-- custom html -->
            
            <!-- // graphe1 !! -->
           <canvas id="Chart1" width="600" height="310"></canvas>   
           <div id="Datatable" style='width:auto;height:auto;display:none;'>
               <p id="first">${description}</p><br>
           </div>    
            
        </div>
           
           
        <!------ Bar droite ----->
        <div class="grpelem" id="u847">
            
            <select id="metriqes" multiple size="5" onchange="synch_metrique();">
              <option value="Rappel">Rappel</option>
              <option value="Precision">Precision</option>
              <option value="F_mesure">F_mesure</option>
              <option value="MAE">MAE</option>
              <option value="Accuracy">Accuracy</option>
              <option value="Coverage">Coverage</option>
            </select>
             
            <div id="div_telecharger" class="inv">
                <label id="Tlabel">Sauvgarder En :</label>
                <img id="excel_img" src="imgs/excel.png" onclick="Texcel();" role="button"/>
                <img id="pdf_img" src="imgs/pdf.png" onclick="Tpdf();" role="button"/>
            </div>   
    
            <p id="config" class="Button" role="button">Config</p>
                          
        </div>
           
        <!-- labels sougraph 1 -->   
        <div class="clearfix grpelem" id="u859-4"><!-- content -->
         <p id="cent"></p>
        </div>
              
       </div>
          <p class="colelem" id="u885-5" style="margin-left: 80px; margin-top:10px">${description}</p>
       </div>
         
         
         
      <div class="Container invi clearfix grpelem" id="u588"><!-- group -->
          
          <!-- 2eme page !!!!  Outil D'affichage-->
          
          <!--div du config -->
          <div  id="conf_outils_affichage">
              
          <p class="titre" id="titre2"> Configuration  </p>
              
        <select id="type_graph2" >
          <option selected disabled class="ch">type du graph</option> 
          <option value="line">Line Chart</option>
          <option value="bar">Bar Chart</option>
          <option value="horizontalBar">horizontalBar Chart</option>
          <option value="radar">Radar Chart</option>
          <option value="polarArea">PolarArea Chart</option>
          <option value="pie">Pie Chart</option>
          <option value="doughnut">Doughnut Chart</option>
          <option value="bubble">Bubble Chart</option>
        </select> 
           
        <!-- nom du graph -->
         <div id="noms_graphs" >
          <input type="text" id="nom_graph1"  placeholder="Nom du graph 1 " class="inps_noms_graphs" />  
        </div>
      
        <!-- text Areas  --> 
        <div id="text_zone_outil" >
         <textarea id="l_x" class="list_vals_graph2" placeholder=" Vals X" cols="50" rows="50"></textarea>
         <textarea id="l_Y1" class="list_vals_graph2" placeholder=" Vals Y du graph 1" cols="50" rows="50"></textarea>
         <p id="Supp_G" class="w3-btn-floating w3-red w3-disabled bouton_supp" onclick="supprimer_graph();">-</p>
         <p id="Add_G" class="w3-btn-floating w3-teal bouton_ajout" onclick="ajouter_graph();">+</p>              
        </div>
      
        <!-- select zone -->       
        <div id="select_zone_top">    
            <select id="select_color1" class="select_outil" >
              <option selected disabled class="ch">Couleur</option> 
              <option value="blue">Blue</option>
              <option value="red">Rouge</option>
              <option value="yellow">Jaune</option>
              <option value="green">Vert</option>
            </select> 
        </div>
        <div id="select_zone_bottom">      
            <select id="select_marqueur1" class="select_outil" >
              <option selected disabled class="ch">marqueur</option> 
              <option value="circle">circle</option>
              <option value="star">star</option>
              <option value="triangle">triangle</option>
              <option value="rect">rect</option>
              <option value="rectRot">rectRot</option>
              <option value="rectRounded">rectRounded</option>
              <option value="cross">cross</option>
              <option value="crossRot">crossRot</option>           
            </select>             
         </div>    
             
           <p id="btt_affiche" class="Button" onclick="synchro_graph2();">affiche</p> 
              
        </div>
          <!-- div du graph2 d'outil -->
          <div id="Zone_graph_2"> 
              <!-- 2eme graph !! -->
              <canvas id="Chart2" width="550" height="250"></canvas>   
              <a  id="btt_modif" class="Button" onclick="modifier();">Modifier</a>   
          </div>
          
      </div>
     </div>
         
         </form>
         </main>
        
    </div>
   </div>
      
   <div class="verticalspacer" data-offset-top="0" data-content-above-spacer="673" data-content-below-spacer="32"></div>
   <div class="browser_width colelem" id="u110-bw">
    <div id="u110"><!-- group -->
     <div class="clearfix" id="u110_align_to_page">
      <div class="clearfix grpelem" id="u113-5"><!-- content -->
       <p id="u113-3"><span id="u113">&nbsp;&nbsp;&nbsp;&nbsp; </span><span id="u113-2">copyright&nbsp; &nbsp; 2016 - 2017&nbsp;&nbsp;&nbsp; EL DJAZAERY&nbsp; AZZOUZ&nbsp; TEAM</span></p>
      </div>
     </div>
    </div>
   </div>
   </div>
     
<script>
var canvas = document.getElementById('Chart1');
    
var data = {
    labels : ${L_echantis}, 
    /*title:{
        text: "FC classique"  
      },*/
    datasets: [ ]
};
    
var option = {
	scales: {
      yAxes: [{
        ticks: {
           beginAtZero:true
          }
       }]},
    animation: {
        duration:3000
    }
};

var chart1 = new Chart(canvas,{
  type: 'line',
  data:data,
  options:option
});

    
     var canvas2 = document.getElementById('Chart2');
     
     var data2 = {
     labels : ["labels"] , 
     /*title:{
         text: "Outils D'affichage"  
       },*/
     datasets: [
          {  
            label: "label" , /* graph Numero i*/ 
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(220,220,220,1)",
            borderColor: "rgba(220,220,220,1)" ,
            borderCapStyle: 'butt', 
            borderDashOffset: 0.5,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(220,220,220,1)" ,
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,  // BorderWidth: 5,    
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(220,220,220,1)" ,
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            pointStyle: "star" ,
            data: [] ,
        }
    
    ]};
        

    var Chart2 = new Chart(canvas2,{
      type: "bar" ,
      data:data2,
      options:option
    });    


    
</script>     
     
     
     
   <!--  /*******   Main Script ******/ -->
<script>
    
 var type_graph_1 = 'line';

    var list_echants = [];
    var list_seuils  = [];
    var list_KNN     = [];
    
// main    
window.onload = function() {
    
    /* Echantillonage User Item Voisin */
    list_echants = ${L_echantis};
    /* User Based */
    if(${fc_type_based}==0)
        list_echants[list_echants.length-1] = list_echants[list_echants.length-1] + " User" ;
    /* Item Based */
    if(${fc_type_based}==1)
        list_echants[list_echants.length-1] = list_echants[list_echants.length-1] + " Item" ;    

    /* Seuils */
    list_seuils = ${L_seuils};
    list_seuils[list_seuils.length-1] = list_seuils[list_seuils.length-1] + " Seuil" ;
    
    /* KNN */
    list_KNN = ${L_KNN};
    list_KNN[list_KNN.length-1] = list_KNN[list_KNN.length-1] + " Voisin" ;
    
    // slope One !!
    if(${bar_graph}) type_graph_1='bar';
        
   synch_metrique();
    
    
// Seuils    
if( ${graph} == 1 ){
     charger_ghraph1();
 }
    
// Echantillons
if( ${graph} == 0){
     charger_graph2();  
 }
    
// KNN    
if( ${graph} == 2 ){
     charger_graph3();
 } 
    
// Slope One Kmean Knn clasification collony de fourmis
if( ${graph} == 3 ){
     charger_ghraph4();
 } 
    
 if(${data}==1){
    $('#div_telecharger').fadeIn('slow');
 }
  
    
}



function dis_sim(){
    var rac = document.getElementById('Iu');
    var selct = rac.options[rac.selectedIndex].value;

    if(selct=='slope One'){
        
        if(document.getElementById('page_acctuel').value == 'fc_multi')   $('#fc_type_conf').fadeOut(0);
        
        
        $('#part1').fadeOut(0);

        slop_one=true;
        
        document.getElementById('COS').checked = false;
        document.getElementById('PER').checked = false;
        document.getElementById('COS').setAttribute("disabled","");      
        document.getElementById('PER').setAttribute("disabled","");     
        document.getElementById('Es').setAttribute("disabled","");     
    }else{
        document.getElementById('COS').removeAttribute('disabled');
        document.getElementById('PER').removeAttribute('disabled');
        document.getElementById('Es').removeAttribute('disabled');
        
        
        if(document.getElementById('page_acctuel').value == 'fc_multi')    $('#fc_type_conf'). fadeIn(0);

        $('#part1').fadeIn('slow');
       
        slop_one=false;
        sauvgarde();
    }
}

 // graph Seuils
function charger_ghraph1(){
    chart1.data.labels = list_seuils ;
    chart1.update();
}
    
    
// graph Echantillons  // Seuils Users Voisins  
function charger_graph2(){ 
    chart1.data.labels = list_echants ;
    chart1.update(); 
}    

// KNN    
function charger_graph3(){
    chart1.data.labels = list_KNN ;
    chart1.update();     
}    

// Slope One Kmean Knn clasification collony de fourmis
function charger_ghraph4(){
    chart1.data.labels = [${nb_users}] ;
    chart1.update();     
}    
    
    
function graph_Rappel(){
    var label = "Rappel Cos" ;
    if( ${cos} == 0 )  if(${data} == 1) label = 'Rappel Person' ; else label = 'Rappel' ;
    
    chart1.data.datasets.push(
        {
            label: label,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(75,192,192,0.30)",       
            borderColor: "rgba(75,192,192,1)",
            borderCapStyle: 'butt',
            borderDash: ["test 1 " , " test 2"], 
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(75,192,192,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,  // BorderWidth: 5,    
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(75,192,192,1)",
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            data:${Rappel}, 
        });
    if( ${doub} == 1){
        chart1.data.datasets.push(
            {
                label: "Rappel Pearson",
                fill: false,
                lineTension: 0.1,
                backgroundColor: "rgba(35,34,34,0.3)",
                borderColor: "rgba(0,0,0,1)",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(0,0,0,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,  // BorderWidth: 5,    
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(0,0,0,1)",
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 5,
                pointHitRadius: 10, 
                pointStyle:"rectRot",
                data:${Rappel_per}, 
            });
    }
    chart1.update();
}    

    
function graph_Precision(){
    var label = "Precision Cos" ;
    if( ${cos} == 0 )  if(${data} == 1) label = 'Precision Person' ; else label = 'Precision' ;
    
    chart1.data.datasets.push(
        {
            label: label ,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(0, 15, 104,0.2)",
            borderColor: "rgba(0, 15, 104,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(0, 15, 104,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(0, 15, 104,1)",  
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            pointStyle:"triangle",   
            data:${Precision},
        });
    if( ${doub} == 1){
        chart1.data.datasets.push(
            {
                label: "Precision Pearson",
                fill: false,
                lineTension: 0.1,
                backgroundColor: "rgba(244,240,0,0.3)",
                borderColor: "rgba(244,240,0,1)",  
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(244,240,0,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(244,240,0,1)",
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 5,
                pointHitRadius: 10,
                pointStyle:"star",
                data:${Precision_per},         
            });
    }
    chart1.update();
}
    
    
function graph_F_mesure(){
    var label = "F_mesure Cos" ;
    if( ${cos} == 0 )  if(${data} == 1) label = 'F_mesure Person' ; else label = 'F_mesure' ;
    
    chart1.data.datasets.push(
        {
            label: label ,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(139, 255, 86,0.2)",
            borderColor: "rgba(82,255,2,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(82,255,2,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(82,255,2,1)",
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            pointStyle:"rect",
            data:${F_mesure},
        });
    if( ${doub} == 1){
        chart1.data.datasets.push(
            {
                label: "F_musure Pearson",
                fill: false,
                lineTension: 0.1,
                backgroundColor: "rgba(130, 0, 119,0.5)",
                borderColor: "rgba(130, 0, 119,1)",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(130, 0, 119,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(130, 0, 119,1)",
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 5,
                pointHitRadius: 10,
                pointStyle:"rectRounded",
                data:${F_mesure_per},
            });
    }
    chart1.update();
}
    
    
function graph_MAE(){
    var label = "MAE Cos" ;
    if( ${cos} == 0 )  if(${data} == 1) label = 'MAE Person' ; else label = 'MAE' ;
    
    chart1.data.datasets.push(
        {
            label: label ,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(255, 0, 0,0.2)",
            borderColor: "rgba(255, 0, 0,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(255, 0, 0,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor:"rgba(255, 0, 0,1)", 
            pointHoverBorderColor:"rgba(220,220,220,1)" ,
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            pointStyle:"crossRot",
            data:${MAE},
        });
    if( ${doub} == 1){
        chart1.data.datasets.push(
            {
                label: "MAE Pearson",
                fill: false,
                lineTension: 0.1,
                backgroundColor: "rgba(94, 0, 0,0.2)",
                borderColor: "rgba(94, 0, 0,1)",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(94, 0, 0,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(94, 0, 0,1)",  
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 5,
                pointHitRadius: 10,
                pointStyle:"crossRot",
                data:${MAE_per},
            });
    }
    chart1.update();
}
    
    
function graph_Accuracy(){
    var label = "Accuracy Cos" ;
    if( ${cos} == 0 )  if(${data} == 1) label = 'Accuracy Person' ; else label = 'Accuracy' ;
    
    chart1.data.datasets.push(
        {
            label: label,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(238, 0, 255,0.3)",
            borderColor: "rgba(238, 0, 255,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(238, 0, 255,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor:"rgba(238, 0, 255,1)", 
            pointHoverBorderColor:"rgba(220,220,220,1)" ,
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            pointStyle:"star",
            data:${Accuracy},
        });
    if( ${doub} == 1){
        chart1.data.datasets.push(
            {
                label: "Accuracy Pearson",
                fill: false,
                lineTension: 0.1,
                backgroundColor: "rgba(94, 0, 0,0.2)",
                borderColor: "rgba(94, 0, 0,1)",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(94, 0, 0,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(94, 0, 0,1)",  
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 5,
                pointHitRadius: 10,
                pointStyle:"crossRot",
                data:${Accuracy_per},
            });
    }
    chart1.update();
}    

    
function graph_Coverage(){
    var label = "Coverage Cos" ;
    if( ${cos} == 0 )  if(${data} == 1) label = 'Coverage Person' ; else label = 'Coverage' ;
    
    chart1.data.datasets.push(
        {
            label: label ,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(122, 88, 124,0.3)",
            borderColor: "rgba(122, 88, 124,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(122, 88, 124,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor:"rgba(122, 88, 124,1)", 
            pointHoverBorderColor:"rgba(220,220,220,1)" ,
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            pointStyle:"rectRounded",
            data:${Coverage},
        });
    if( ${doub} == 1){
        chart1.data.datasets.push(
            {
                label: "Coverage Pearson",
                fill: false,
                lineTension: 0.1,
                backgroundColor: "rgba(94, 0, 0,0.2)",
                borderColor: "rgba(94, 0, 0,1)",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(94, 0, 0,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(94, 0, 0,1)",  
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 5,
                pointHitRadius: 10,
                pointStyle:"crossRot",
                data:${Coverage_per},
            });
    }
    chart1.update();
}    
    
var slop_one=false;
function synch_graph(){
    
    if(slop_one) return;
    
    var rac = document.getElementById("Es");
    var selct = rac.options[rac.selectedIndex].value;
           
    if( selct == "Seuil" ) {
        $('#Ginps_seuils'). fadeOut(0);
        $('#Ginps_KNN').    fadeOut(0);
        $('#Ginps_Echants').fadeIn ();
    }
    if(selct == "Echantillonnage"){
        $('#Ginps_Echants').fadeOut(0);
        $('#Ginps_KNN').    fadeOut(0);
        $('#Ginps_seuils'). fadeIn ();
    }
    if(selct == "KNN"){
        $('#Ginps_Echants').fadeOut(0);
        $('#Ginps_seuils'). fadeOut(0);
        $('#Ginps_KNN').    fadeIn ();
    }
    
    
    if(${data} == 1 ){ return ;}

    if( selct == "Seuil" )         charger_ghraph1();
    if(selct == "Echantillonnage") charger_graph2();
    if(selct == "KNN")             charger_graph3(); 
}

    
$(document).ready(function(){
    $('#config').click(function(){
        $('#slid').slideToggle('slow');
    });
})

$(document).keyup(function(e) {
     if (e.keyCode == 27) { // escape key maps to keycode `27`
       $('#slid').slideUp('slow');
    }
});

$(document).mouseup(function (e){
    var container = $("#slid") , container2 = $("#config");
    if (!$(e.target).is(container) && !$.contains(container[0],e.target) && !$(e.target).is(container2) && !$.contains(container2[0],e.target) ){
    	$('#slid').slideUp('slow');
    }
    var container = $("#bar_gauche") , container2 = $("#lbel_menu");
    if (!$(e.target).is(container) && !$.contains(container[0],e.target) && !$(e.target).is(container2) && !$.contains(container2[0],e.target) ){
        document.getElementById("menuToggle").checked = false;
    }
});
    
    
    
    var echant = 1;
    function ajouter_echant() {
        if(echant==1){
           $('#Supp_E').removeClass('w3-disabled');
        }
        echant++;
        $('#Supp_E').before('<input type="number" id="ech'+echant+'" placeholder="N '+echant+'" min="1" onchange="sauvgarde();" required/>');
        $('#ech'+echant).fadeIn(1000);
    }

    function supprimer_echant() {
        if(echant>1){
          $('#ech'+echant).remove();
          echant--;
        }
        if(echant==1){
            $('#Supp_E').addClass('w3-disabled');
        }
        sauvgarde();
    }

    
    var seuil = 1 , min = -1 , value=0 ;
    function ajouter_seuil() {
        if(seuil==1){
           $('#Supp_S').removeClass('w3-disabled');
        }
        seuil++;
       $('#Supp_S').before
         ('<input type="number" id="in'+seuil+'" placeholder="seuil '+seuil+'" step="0.1" min="'+min+'" max="1" onchange="sauvgarde();" required/>');
        
        $('#in'+seuil).fadeIn(1000);
    }
    
    function supprimer_seuil(){
        if(seuil>1){
          $('#in'+seuil).remove();
          seuil--;
        }
        if(seuil==1){
            $('#Supp_S').addClass('w3-disabled');
        }
        sauvgarde();
    }  
    
    var KNN = 1 ; //  
    function add_KNN() {
        if(KNN==1){
           $('#supp_KNN').removeClass('w3-disabled');
        }
        KNN++;
       $('#supp_KNN').before
         ('<input type="number" id="K'+KNN+'" placeholder="K '+KNN+'" min="1" onchange="sauvgarde();" required/>');
        
        $('#K'+KNN).fadeIn(1000);
    }
    
    function supp_KNN(){
        if(KNN>1){
          $('#K'+KNN).remove();
          KNN--;
        }
        if(KNN==1){
            $('#supp_KNN').addClass('w3-disabled');
        }
        sauvgarde();
    }
   
function doAlert(checkboxElem) {
             
    if (checkboxElem.checked) {
    
        min=0;
        
        document.getElementById("seuil_fix").setAttribute('min','0'); 
        document.getElementById("seuil_fix").setAttribute('value','0.5'); 
        
        for (var int = 1; int <= seuil; int++) {
            document.getElementById("in"+int+"").setAttribute('min','0');
        }
    } 
    else{
        min=-1;
    	
        document.getElementById("seuil_fix").setAttribute('min','-1'); 
        document.getElementById("seuil_fix").setAttribute('value','0');
        
        for (var int = 1; int <=seuil; int++) {
            document.getElementById("in"+int+"").setAttribute('min','-1');
        } 
    }
    sauvgarde();
}
    
    
    function sauvgarde(){ 

        var list_echants_local=['0'] , list_KNN_local=['0'] , list_seuils_local=[] ;
        
        document.getElementById("L_E").value = "0"; document.getElementById("L_K").value = "0"; document.getElementById("L_S").value = "" ;
        
        
        //// ----- sauvgarder Seuils ------ ////
        for(var i=1 ; i<=seuil ; i++){
             list_seuils_local.push(document.getElementById('in'+i).value) ;
        }list_seuils_local = _.uniq(list_seuils_local);list_seuils_local = asc(list_seuils_local);
        
        document.getElementById("L_S").value = list_seuils_local[0] ;
        
        for(var i=1 ; i<list_seuils_local.length ; i++){
            document.getElementById("L_S").value = document.getElementById("L_S").value + "," + list_seuils_local[i] ;
        }
        document.getElementById("L_S").value = document.getElementById("L_S").value + "," +  document.getElementById('echant_fix').value ;
        
        
        //// -----  sauvgarder Echantillonages ------ ////        
        for(var j=1 ; j<=echant ; j++){
              list_echants_local.push(document.getElementById('ech'+j).value);
        }list_echants_local = _.uniq(list_echants_local);list_echants_local = asc(list_echants_local);

        for(var j=1 ; j<list_echants_local.length ; j++){
            document.getElementById("L_E").value = document.getElementById("L_E").value + "," + list_echants_local[j] ;
        }
        document.getElementById("L_E").value = document.getElementById("L_E").value + "," +  document.getElementById('seuil_fix').value ;
            
        //// -------- sauvgarder ------------- ////
        for(var k=1 ; k<=KNN ; k++){
            list_KNN_local.push(document.getElementById('K'+k).value);
        }list_KNN_local = _.unique(list_KNN_local); list_KNN_local =asc(list_KNN_local);
        
        for(var k=1 ; k<list_KNN_local.length ; k++){
             console.log(k);
             document.getElementById("L_K").value = document.getElementById("L_K").value + "," + list_KNN_local[k] ;            
        }
        document.getElementById("L_K").value = document.getElementById("L_K").value + "," + document.getElementById("ech_KNN").value   ;
      
        
        //// ---- sauvgarder criteres --- priorite1 L_C ////
        document.getElementById('L_C').value = document.getElementById('priorite1').value; 
        for(var i=2;i<5;i++){
            document.getElementById('L_C').value = document.getElementById('L_C').value + ',' + document.getElementById('priorite'+i).value ;
        }
        
                
        // synchroniser le graph "affichage"
        if(${data}==0){
            
            list_seuils  = list_seuils_local;
            list_echants = list_echants_local;
            list_KNN     = list_KNN_local;
            
            list_seuils [list_seuils.length-1 ] = list_seuils[list_seuils.length-1]   + ' Seuil'  ;
            list_KNN    [list_KNN.length-1    ] = list_KNN[list_KNN.length-1      ]   + ' Voisin' ;
            
            
            var rac = document.getElementById('Iu');
            var selct = rac.options[rac.selectedIndex].value;
            
            if(selct=='User Based')
              list_echants[list_echants.length-1] = list_echants[list_echants.length-1] + ' User'  ;
            if(selct=='Item Based')
              list_echants[list_echants.length-1] = list_echants[list_echants.length-1] + ' Item'  ;
                
            
            synch_graph(); 
        }
        
        
    }
    
        // Array.sort()
        const sub = x => y => y - x;
        const flip = f => x => y => f (y) (x);
        const uncurry = f => (x,y) => f (x) (y);
        const sort = f => xs => xs.sort(uncurry (f));
            
        const asc = sort (flip (sub));
        const desc = sort (sub);        
    
    
    function set_url(){
       document.forms[0].action ="";
       document.forms[0].submit;
    }
    
function G1destroy(){
    var list_x = chart1.data.labels ;

    canvas = null ; 
    canvas = document.getElementById('Chart1');
           
    chart1.destroy();
    data = {
         labels : ${L_echantis}, 
         datasets: []        
    };
    chart1 = new Chart(canvas,{
      type: type_graph_1,
      data:data,
      options:option
     });
    
    chart1.data.labels = list_x ;
}
    
    
function synch_metrique(){
    G1destroy();
    
    metrique = document.getElementById("metriqes");
    var graphes_selected = getSelectValues(metrique); 
    
    if(   graphes_selected.length == 0          )  graph_Rappel();
    if( _.contains(graphes_selected,"Rappel")   )  graph_Rappel();
    if( _.contains(graphes_selected,"Precision"))  graph_Precision();
    if( _.contains(graphes_selected,"F_mesure") )  graph_F_mesure();
    if( _.contains(graphes_selected,"MAE")      )  graph_MAE();
    if( _.contains(graphes_selected,"Accuracy") )  graph_Accuracy();
    if( _.contains(graphes_selected,"Coverage") )  graph_Coverage();
    
}    
    
 function getSelectValues(select) {
  var result = [];
  var options = select && select.options;
  var opt;

  for (var i=0, iLen=options.length; i<iLen; i++) {
    opt = options[i];

    if (opt.selected) {
      result.push(opt.value || opt.text);
    }
  }
  return result;
}    
    
    /********************** ----- Sauvgarder data ----- ****************************/ 
var dataToTable = function (data , id) {
    var html = '<table id="'+id+'" >';
    html += '<tr> <th style="width:auto">#</th>';
 
    var columnCount = 0;
    jQuery.each(data.datasets, function (idx, item) {
        if(item !='0' && item !=0 ){ 
            html += '<th style="background-color:' + item.fillColor + ';">' + item.label + '</th>';
            columnCount += 1;
        }
    });
 
    jQuery.each(data.labels, function (idx, item) {
        if(item !='0' && item !=0 ){ 
          html += '<tr><td>' + item + '</td>';
        }
        for (i = 0; i < columnCount; i++) {
          if(data.datasets[i].data[idx] != 0){    
            html += '<td style="background-color:' + data.datasets[i].backgroundColor + ';">' +  (data.datasets[i].data[idx] === '0' ? '-' : data.datasets[i].data[idx]) + '</td>';
           }
        }
            html += '</tr>';
    });
 
    html += '</table>';
 
    return html;
}; 
    
    
var download = function (name, content, mimetype) {
    if ($('.download-trigger').length === 0) {
        jQuery('body').append('<span style="display:none;"><a class="download-trigger"></a></span>')
    }
    var a = $('.download-trigger')[0];
 
    a.href = window.URL.createObjectURL(new Blob([content], {
        type: mimetype
    }));
    a.download = name;
    a.textContent = 'Download';
    a.click();
};
    
    
function Texcel(){
    if(${data} == 0 ){ return null;}

    G1destroy();
    graph_Rappel(); graph_Precision(); graph_F_mesure(); graph_MAE(); graph_Accuracy(); graph_Coverage();
    var dataF = dataToTable(data,'tableData') ;
    synch_metrique();
    
    download("Test.xls", dataF , 'application/vnd.ms-excel');
}    
 

function Tpdf(){
    
   $('#Chart1').css('background-color', 'rgba(0, 0, 0, 0)');    
   var newCanvas = document.querySelector('#Chart1');
   //create image from dummy canvas
	var newCanvasImg = newCanvas.toDataURL("image/png", 1.0);
   
  	//creates PDF from img
//	var doc = new jsPDF('landscape'); //'landscape'  new jsPDF("l", "pt", "letter");
    var doc = new jsPDF("landscap","pt","a4");
    
	doc.setFontSize(10);
    var date = new Date();
	doc.text(50, 50, " "+date);
    
    doc.setFontSize(15);
    doc.setFontStyle("bolditalic");
	doc.text(50, 90, "Analyse FC : " + document.getElementById("u885-5").textContent ); // document.getElementById("u885-5")
    
	doc.addImage(newCanvasImg, 'png', 50 , 100, 700, 350 );
    /* add data tables and description labels */
    doc.addPage('a4','l');
    doc = addData(doc);
	doc.save('Fc_Classique.pdf');
}    
    
var first = true;  
function addData(doc){ 
   
   if(first){
       G1destroy();
       if(${doub} == 0) {
           graph_Rappel(); graph_Precision(); graph_F_mesure(); graph_MAE(); graph_Accuracy(); graph_Coverage();
           $('#first').before(dataToTable(data,'tableData'));
       }else{
           graph_Rappel(); graph_Precision(); graph_F_mesure();
           $('#first').before(dataToTable(data,'tableData'));
           G1destroy();
           graph_MAE(); graph_Accuracy(); graph_Coverage();
           $('#first').before(dataToTable(data,'tableData_Double'));
       }
       synch_metrique();
       first = false ;
   }

  var table = tableToJson($('#tableData').get(0));
    
  doc.setFontSize(15);
  doc.setFontStyle("bolditalic"); 
  doc.text(20, 40,'Table de donees');
    
  $.each(table, function(i, row){
        $.each(row, function(j,cell){
            if(i==0 | j=='#'){
                doc.setFontSize(12);
                doc.setFontStyle("bolditalic"); 
            }else{
                doc.setFontSize(10);                
            }
             doc.cell(10,80,110,20,cell,i);	
         });
   });
        
   if(${doub} == 1) {
     var table = tableToJson($('#tableData_Double').get(0));

      $.each(table, function(i, row){
            $.each(row, function(j,cell){
                if(i==0  || j=='#' ){
                    doc.setFontSize(12);
                    doc.setFontStyle("bolditalic"); 
                }else{
                    doc.setFontSize(10);                
                }
                 doc.cell(10,80,110,20,cell,i);	
             });
       });       
   }
    
    return doc ;
}    
    
function tableToJson(table) {
    var data = [];

    // first row needs to be headers
    var headers = [];
    for (var i=0; i<table.rows[0].cells.length; i++) {
        headers[i] = table.rows[0].cells[i].innerHTML.toLowerCase().replace(/ /gi,' ');
    }
    data.push(headers);
    // go through cells
    for (var i=1; i<table.rows.length; i++) {

        var tableRow = table.rows[i];
        var rowData = {};

        for (var j=0; j<tableRow.cells.length; j++) {

            rowData[ headers[j] ] = tableRow.cells[j].innerHTML;

        }

        data.push(rowData);
    }       

    return data;
}
    
    /********************** ---- Outil D'affichage ----- *************************/
    
    
    function outil_affichage_part1(type_du_graph ,labels){
       canvas2 = null ;
       canvas2 = document.getElementById('Chart2');
       
       Chart2.destroy(); 
       Chart2 = new Chart(canvas2,{
       type: type_du_graph ,
       data:data2,
       options:option
      });    

      Chart2.data.labels = labels ;
      Chart2.update();         
    }

    
    function outil_affichage_part2(vals ,label ,pointstyle ,color1 ,color2 ,i){
      Chart2.data.datasets[i].label = label ;
      Chart2.data.datasets[i].data = vals ;
      Chart2.data.datasets[i].pointStyle = pointstyle ;
      Chart2.data.datasets[i].backgroundColor = color2 ;
      Chart2.data.datasets[i].borderColor = color1 ;
      Chart2.data.datasets[i].pointBorderColor = color1 ;
      Chart2.data.datasets[i].pointHoverBackgroundColor = color1 ;
      Chart2.update();                
    }
    
    
    var nb_graph = 1 ;
    function ajouter_graph(){

      if(nb_graph == 1){
         $('#Supp_G').removeClass('w3-disabled');
      }
      
       nb_graph++;
      /* add nom du graph */
      $('#noms_graphs').append('<input type="text" id="nom_graph'+nb_graph+'"  placeholder="Nom du graph '+nb_graph+' " class="inps_noms_graphs" />');
        
      /* add vals Y */
      $('#Supp_G').before('<textarea id="l_Y'+nb_graph+'" class="list_vals_graph2" placeholder=" Vals Y du graph '+nb_graph+'" cols="50" rows="50"></textarea>');
      
      /* add select color */
       $('#select_zone_top').append('<select id="select_color'+nb_graph+'" class="select_outil" ><option selected disabled class="ch">Couleur</option> <option value="blue">Blue</option><option value="red">Rouge</option><option value="yellow">Jaune</option><option value="green">Vert</option></select>');
        
        /* add select marqueur*/
        $('#select_zone_bottom').append('<select id="select_marqueur'+nb_graph+'" class="select_outil" ><option selected disabled class="ch">marqueur</option><option value="circle">circle</option><option value="star">star</option><option value="triangle">triangle</option><option value="rect">rect</option><option value="rectRot">rectRot</option><option value="rectRounded">rectRounded</option><option value="cross">cross</option><option value="crossRot">crossRot</option></select>');
    
        /* ajouter data au datasets du graph**/
        ajouter_data_graph2();
    }
    
    
    function supprimer_graph(){
        
      if(nb_graph==2){
         $('#Supp_G').addClass('w3-disabled');
      }if(nb_graph==1) return; 
        
      /* supprimer nom du graph*/
        $('#nom_graph'+nb_graph).remove();
      
      /* supprimer vals Y*/
        $('#l_Y'+nb_graph).remove();
        
      /* supprimer select color*/
        $('#select_color'+nb_graph).remove();
        
      /* supprimer select marqueur*/
        $('#select_marqueur'+nb_graph).remove();

        suprimer_data_graph2();
     nb_graph--;         
    }
    
    
    
  function ajouter_data_graph2(){
      Chart2.data.datasets.push({
            label: "label" , /* graph Numero i*/ 
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(220,220,220,1)",
            borderColor: "rgba(220,220,220,1)" ,
            borderCapStyle: 'butt', 
            borderDashOffset: 0.5,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(220,220,220,1)" ,
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,  // BorderWidth: 5,    
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(220,220,220,1)" ,
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointHoverBorderWidth: 2,
            pointRadius: 7,
            pointHitRadius: 7,
            pointStyle: "star" ,
            data: [] ,
        });
      Chart2.update();
  } 
   
function suprimer_data_graph2(){
    Chart2.data.datasets.splice(Chart2.data.datasets.length - 1 );
    Chart2.update();
}    

  
  function synchro_graph2(){ /*** l_x l_Y1 select_color select_type  */
      
      var select_index = document.getElementById('type_graph2').selectedIndex ;
          if(select_index==0) select_index = 1 ;
      var type_du_graph = document.getElementById("type_graph2").options[select_index].value ;
      
      var labels = Split("l_x");
      outil_affichage_part1(type_du_graph,labels);
      
      for(var i=0;i<nb_graph;i++){
        var vals = Split('l_Y'+(i+1));
          
        select_index =  document.getElementById('select_marqueur'+(i+1)).selectedIndex ;
          if(select_index==0) select_index = 1 ;
        var pointstyle = document.getElementById('select_marqueur'+(i+1)).options[select_index].value ;
        
        var colors = color('select_color'+(i+1));
        var color1 = colors[0] ;
        var color2 = colors[1] ; 
          
        var label = document.getElementById('nom_graph'+(i+1)).value ;
         
        outil_affichage_part2(vals ,label ,pointstyle ,color1 ,color2 ,i);
      }
          
      $('#conf_outils_affichage').fadeOut();
      $('#Zone_graph_2').fadeIn();
      
  }
    
      
  function Split(id){
      var list = document.getElementById(id).value.split("\n") ;
      return  _.without(list,'') ;
  }    
  
  function color(id){
    var blue = ["rgba(75,192,192,1)","rgba(75,192,192,0.3)"];
    var red = ["rgba(255, 0, 0,1)","rgba(255, 0, 0,0.3)"];
    var yellow = ["rgba(244,240,0,1)","rgba(244,240,0,0.3)"];
    var green = ["rgba(82,255,2,1)","rgba(82,255,2,0.3)"];         
      
    var rac = document.getElementById(id);
    var selct = rac.options[rac.selectedIndex].value;
    
    switch (selct){
        case "blue": return blue ;
        case "red" : return red ;
        case "yellow" : return yellow ;
        case "green" : return green ;
        default : return blue;
    }
      
  }    
    
  function modifier(){
      $('#Zone_graph_2').fadeOut();
      $('#conf_outils_affichage').fadeIn();
  }

    
    
    /************ actualiser label of input file *******************/
var valide=true , inp_label = '';
  'use strict';
;(function ( document, window, index ){
	var inputs = document.querySelectorAll('.inputfile');
	Array.prototype.forEach.call( inputs, function( input ) {
		var label	 = input.nextElementSibling,
			labelVal = label.innerHTML;
            inp_label=label;
        
		input.addEventListener('change', function( e ) {
			var fileName = '';
			if( this.files && this.files.length ==3 ){
                  for(var i=0;i<this.files.length;i++){
                      if(this.files[i].name.split('.').pop().toLowerCase()  != 'xls'  &&
                         this.files[i].name.split('.').pop().toLowerCase()  != 'xlsx' &&
                         this.files[i].name.split('.').pop().toLowerCase()  != 'csv' ) { valide=false; break; }
                      
                      if(this.files[i].name.split('.')[0].toLowerCase().indexOf('user')   == -1  &&
                         this.files[i].name.split('.')[0].toLowerCase().indexOf('item')   == -1  &&
                         this.files[i].name.split('.')[0].toLowerCase().indexOf('rating') == -1 ) { valide=false; break; }
                      
                      // taill du chaque fichier moins que 100 MB
                      if(Math.floor(this.files[i].size/(1024 * 1024)) > 100) { valide=false; break; }                
                  }
            }else valide=false;
            
            if(!valide){
                $('#file').val("");
                label.innerHTML = 'Choose a file...';
                alert('Fichies Non Valide !!!');
            }else{
                fileName = ( this.getAttribute( 'data-multiple-caption' ) || '' ).replace( '{count}', this.files.length );
                label.querySelector('span').innerHTML = fileName;
            } 
            // reintialiser 
            valide=true;
		});

		// Firefox bug fix
		input.addEventListener( 'focus', function(){ input.classList.add( 'has-focus' ); });
		input.addEventListener( 'blur', function(){ input.classList.remove( 'has-focus' ); });
        
	});
}(document, window, 0 ));

    
function slide_vide(){
    $('#fc_conf').fadeOut(0); 
    $('#base_conf').fadeOut(0);
    $('#fc_type_conf').fadeOut(0);
    $('#fc_classif_KNN').fadeOut(0);
    $('#fc_classif_opt' ).fadeOut(0);
    $('#fc_classif_Kmean').fadeOut(0);
    
}    
    
function M1(){
  /*  $("#file").val("");
    inp_label.innerHTML='Choose a file...'; Base*/
    
    if(document.getElementById('page_acctuel')
        .value == 'Base') return ;
    
    slide_vide();
    $('#base_conf').fadeIn();
    
    document.getElementById('page_acctuel')
        .value = 'Base' ;
    
}
    
function M2_1(){
    
    if(document.getElementById('page_acctuel')
        .value == 'fc_mono') return ;
    
    slide_vide();
    $('#part1').fadeIn();
    $('#part2').fadeIn();
    $('#part3').fadeIn();
    $('#fc_conf').fadeIn();
    
    document.getElementById('page_acctuel')
        .value = 'fc_mono' ;
}
        
function M2_2(){
    if(document.getElementById('page_acctuel')
        .value == 'fc_multi') return ;
    
   M2_1();
   if(!slop_one) $('#fc_type_conf').fadeIn();

    document.getElementById('page_acctuel')
        .value = 'fc_multi' ;
}
    
function M3_1(){
    if(document.getElementById('page_acctuel')
        .value == 'fc_Kmean') return ;

    slide_vide();
    $('#part1').fadeOut(0);
    $('#part3').fadeOut(0);
    $('#fc_conf').fadeIn('slow');
    $('#fc_classif_Kmean').fadeIn();

    document.getElementById('page_acctuel')
        .value = 'fc_Kmean' ;
}
        
function M3_2(){
    if(document.getElementById('page_acctuel')
        .value == 'fc_classif_KNN') return ;

   M3_1();
   $('#fc_classif_KNN').fadeIn();    

    document.getElementById('page_acctuel')
        .value = 'fc_classif_KNN' ;
}

function M4(){
    if(document.getElementById('page_acctuel')
        .value == 'fc_Ant_Collony') return ;
    
   slide_vide();
   $('#fc_classif_opt').fadeIn();    

    document.getElementById('page_acctuel')
        .value = 'fc_Ant_Collony' ;
}

    
function loading(){ 
 $('#slid').slideUp();
 $('#couche').slideDown();
 $('#loading_bar').fadeIn();    
}
    
</script>
    
  <!-- Other scripts -->
  <script type="text/javascript">
    
      
      // Update the 'nojs'/'js' class on the html node
    document.documentElement.className = document.documentElement.className.replace(/\bnojs\b/g, 'js');
    // Check that all required assets are uploaded and up-to-date
    if(typeof Muse == "undefined") window.Muse = {}; window.Muse.assets = {"required":["museutils.js", "museconfig.js", "jquery.watch.js", "webpro.js", "musewpslideshow.js", "jquery.museoverlay.js", "touchswipe.js", "require.js", "index.css"], "outOfDate":[]};
      
      
   window.Muse.assets.check=function(d){if(!window.Muse.assets.checked){window.Muse.assets.checked=!0;var b={},c=function(a,b){if(window.getComputedStyle){var c=window.getComputedStyle(a,null);return c&&c.getPropertyValue(b)||c&&c[b]||""}if(document.documentElement.currentStyle)return(c=a.currentStyle)&&c[b]||a.style&&a.style[b]||"";return""},a=function(a){if(a.match(/^rgb/))return a=a.replace(/\s+/g,"").match(/([\d\,]+)/gi)[0].split(","),(parseInt(a[0])<<16)+(parseInt(a[1])<<8)+parseInt(a[2]);if(a.match(/^\#/))return parseInt(a.substr(1),
16);return 0},g=function(g){for(var f=document.getElementsByTagName("link"),h=0;h<f.length;h++)if("text/css"==f[h].type){var i=(f[h].href||"").match(/\/?css\/([\w\-]+\.css)\?crc=(\d+)/);if(!i||!i[1]||!i[2])break;b[i[1]]=i[2]}f=document.createElement("div");f.className="version";f.style.cssText="display:none; width:1px; height:1px;";document.getElementsByTagName("body")[0].appendChild(f);for(h=0;h<Muse.assets.required.length;){var i=Muse.assets.required[h],l=i.match(/([\w\-\.]+)\.(\w+)$/),k=l&&l[1]?
l[1]:null,l=l&&l[2]?l[2]:null;switch(l.toLowerCase()){case "css":k=k.replace(/\W/gi,"_").replace(/^([^a-z])/gi,"_$1");f.className+=" "+k;k=a(c(f,"color"));l=a(c(f,"backgroundColor"));k!=0||l!=0?(Muse.assets.required.splice(h,1),"undefined"!=typeof b[i]&&(k!=b[i]>>>24||l!=(b[i]&16777215))&&Muse.assets.outOfDate.push(i)):h++;f.className="version";break;case "js":h++;break;default:throw Error("Unsupported file type: "+l);}}d?d().jquery!="1.8.3"&&Muse.assets.outOfDate.push("jquery-1.8.3.min.js"):Muse.assets.required.push("jquery-1.8.3.min.js");
f.parentNode.removeChild(f);if(Muse.assets.outOfDate.length||Muse.assets.required.length)f="Certains fichiers sur le serveur sont peut-ÃÂªtre manquants ou incorrects. Videz le cache du navigateur et rÃÂ©essayez. Si le problÃÂ¨me persiste, contactez le crÃÂ©ateur du site.",g&&Muse.assets.outOfDate.length&&(f+="\nOut of date: "+Muse.assets.outOfDate.join(",")),g&&Muse.assets.required.length&&(f+="\nMissing: "+Muse.assets.required.join(",")),alert(f)};location&&location.search&&location.search.match&&location.search.match(/muse_debug/gi)?setTimeout(function(){g(!0)},5E3):g()}};
var muse_init=function(){require.config({baseUrl:""});require(["jquery","museutils","whatinput","jquery.watch","webpro","musewpslideshow","jquery.museoverlay","touchswipe"],function(d){var $ = d;$(document).ready(function(){try{
window.Muse.assets.check($);/* body */
Muse.Utils.transformMarkupToFixBrowserProblemsPreInit();/* body */
Muse.Utils.prepHyperlinks(true);/* body */
Muse.Utils.resizeHeight('.browser_width');/* resize height */
Muse.Utils.requestAnimationFrame(function() { $('body').addClass('initialized'); });/* mark body as initialized */
Muse.Utils.fullPage('#page');/* 100% height page */
Muse.Utils.initWidget('#pamphletu581', ['#bp_infinity'], function(elem) { return new WebPro.Widget.ContentSlideShow(elem, {contentLayout_runtime:'stack',event:'click',deactivationEvent:'none',autoPlay:false,displayInterval:3000,transitionStyle:'fading',transitionDuration:1000,hideAllContentsFirst:false,shuffle:false,enableSwipe:true,resumeAutoplay:true,resumeAutoplayInterval:3000,playOnce:false,autoActivate_runtime:false}); });/* #pamphletu581 */
Muse.Utils.showWidgetsWhenReady();/* body */
Muse.Utils.transformMarkupToFixBrowserProblems();/* body */
}catch(b){if(b&&"function"==typeof b.notify?b.notify():Muse.Assert.fail("Error calling selector function: "+b),false)throw b;}})})};

</script>
  <!-- RequireJS script -->
  <script src="scripts/require.js?crc=4159430777" type="text/javascript" async data-main="scripts/museconfig.js?crc=4179431180" onload="if (requirejs) requirejs.onError = function(requireType, requireModule) { if (requireType && requireType.toString && requireType.toString().indexOf && 0 <= requireType.toString().indexOf('#scripterror')) window.Muse.assets.check(); }" onerror="window.Muse.assets.check();"></script>
   </body>
</html>