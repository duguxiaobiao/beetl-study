DELETE FROM ${tableName}
<%
  if(whereConditions.~size > 0 ){
  %>
  ${buildWhereSql(whereConditions)}
<%}
%>