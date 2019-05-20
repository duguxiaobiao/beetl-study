UPDATE ${tableName} SET
<%
    for(paramsCol in paramsCols){
        if(paramsColLP.last){
          print(paramsCol);
        }else{
          print(paramsCol + ", ");
        }
    }
    print(" )" + "\r\n");
  }else{
    print("");
  }
%>
