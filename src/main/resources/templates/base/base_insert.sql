INSERT INTO ${tableName}
<%
  if(paramsCols.~size > 0){
    print("( ");
    for(paramsCol in paramsCols){
        if(paramsColLP.last){
          print(paramsCol);
        }else{
          print(paramsCol + ", ");
        }
    }
    print(" )" + "\r\n");
  }
%>
VALUES
<%
  if(paramsValues.~size > 0){
    print("( ");
    for(paramsValue in paramsValues){
        if(paramsValueLP.last){
          print("'"+paramsValue+"'");
        }else{
          print("'"+paramsValue+"'" + ", ");
        }
    }
    print(") ");
  }else{
    print("()");
  }
%>
