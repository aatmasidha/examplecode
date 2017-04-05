<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File upload</title>
<!-- <script type="text/javascript" src="js/validation.js">

    </script>
 --><style>
 
 
 
 h1{
color:#004080;
padding:5px;
text-align:center;
}
form{
     width:auto;
     height:auto;
     border:3px solid #f1f1f1;
     background-color:#f2f2f2;
}

.lable{
	position:absolute;
	top:21%;
	left:28%;
	padding:5px,20px;
	fontsize=15px;
	text-align:center;
	cursor:pointer;
	color:#004080;
	
}
.lable1{
	position:absolute;
	top:29%;
	left:28%;
	padding:5px,20px;
	fontsize=15px;
	text-align:center;
	cursor:pointer;
	color:#004080;
	
}
.lable2{
	position:absolute;
	top:39%;
	left:28%;
	padding:5px,20px;
	fontsize=15px;
	text-align:100%;
	cursor:pointer;
	color:#004080;
}
.lable3{
	position:absolute;
	top:48.5%;
	left:28%;
	padding:5px,20px;
	fontsize=15px;
	text-align:100%;
	cursor:pointer;
	color:#004080;
}
 .edittext1{
   top:27%;
	left:37%;
  width:24%;
  padding:5px 5px;
  margin:8px 0;
  display:inline-block;
  boder:1px solid #ccc;
  box-sizzing:boder-box;
  position:absolute;
  
 }
 .edittext2{
 top:37%;
	left:37%;
  width:24%;
  padding:5px 5px;
  margin:8px 0;
  display:inline-block;
  boder:1px solid #ccc;
  box-sizzing:boder-box;
  position:absolute;
 }
 .edittext3{
 top:47%;
	left:37%;
   width:24%;
  padding:5px 5px;
  margin:8px 0;
  display:inline-block;
  boder:1px solid #ccc;
  box-sizzing:boder-box;
  position:absolute;
 }
 #input1{
    top:28.6%;
	position:absolute;
	left:66%;
	
}
#input2{
    top:38.6%;
	position:absolute;
	left:66%;
	
} 
 #input3{
    top:48.6%;
	position:absolute;
	left:66%;
	
} 

.container{
	padding:30%;
	
	
}
.btn{
	position:absolute;
	top:60%;
	left:33%;
	fontsize=15px;
	background:#0066cc;
	width:15%;
	background-color: #3498DB;
    border: none;
    color: #FFFFFF;
    padding: 5px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    
   }
   .btn{
    -webkit-transition-duration: 0.4s; 
     transition-duration: 0.4s;
      }
      .btn{
      background-color:white;
      color:black;
      border:2px solid #0066cc;
      }
.btn:hover{
 background-color:#0066cc;
    color: white;
}
.btn1{
	position:absolute;
	top:60%;
	left:53%;
	fontsize=15px;
	
	background:#ff4d4d;
	width:15%;
	
    border: none;
    color: #FFFFFF;
    padding: 5px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
   
    }
    .btn1{
    -webkit-transition-duration: 0.4s; 
     transition-duration: 0.4s;
      }
      .btn1{
      background-color:white;
      color:black;
      border:2px solid #ff4d4d;
      }
.btn1:hover{
 background-color:#ff4d4d;
    color: white;
}
select{
	top:20%;
	left:37%;
	width:24.6%;
	background-position: center right;
    background-repeat: no-repeat;
    border: 1px solid #AAA;
    box-sizzing:boder-box;
    border-radius: 2px;
    box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
    color: #555;
    font-size: inherit;
    margin: 0;
    overflow: hidden;
    padding: 1px 1px;
    margin:8px 0;
    text-overflow: ellipsis;
    white-space: nowrap;
    position:absolute;
}

</style>

</head>
<body onload="getPropertyDetailsFunction()">
<h1>File Upload Form </h1>

<script>
function getPropertyDetailsFunction() {
    alert("Image is loaded");
}
</script>

<form name="frm" method="get" action="Fileupload.jsp" onSubmit="return uploadOnChange()">
  
	
	
	<div class="container">
		<select>
		<option value="one">One</option>
		<option value="two">Two</option>
		<option value="three">Three</option>
		</select>
		<span class="lable">Property Name</span><br>
		<span class="lable1">Daily Extract file</span><br>
		<input type="text" class="edittext1" id="filename" placeholder="Enter your File name" name="extract file" required>
		<input type="file" accept=".xls,.xlsx" name="user" id="input1"></input><br>
		<span class="lable2">Occupancy file</span>
		<input type="text" class="edittext2" placeholder="Enter your File name" name="occupancyfile" required>
		<input type="file" accept=".xls,.xlsx" name="user" id="input2"> <br>
		<span class="lable3">Channel Manager<br> AVA Report</span><br>
		<input type="text" class="edittext3" id=" channel report" placeholder="Enter your File name" name="channel report" required>
		<input type="file" accept=".xls,.xlsx" name="user" id="input3"></input><br>
		<span class="btn">Run Analysis</span>
        <span class="btn1">Cancel</span>
    </div>
    <%@ page import="
    com.revnomix.isell.common.dto.PropertyDetailsDTO, com.revnomix.isell.business.services.property.PropertyDetailsServicesImpl"%>
    
   <%
	PropertyDetailsServicesImpl tc = new PropertyDetailsServicesImpl();
   tc.getAllTheActivePropertyDetails();
  %>
	
</form>

</body>
</html>
