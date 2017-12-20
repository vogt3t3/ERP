package com.crm.util;
import java.util.List;

import org.josql.Query;
import org.josql.QueryExecutionException;
import org.josql.QueryParseException;
import org.josql.QueryResults;

public class JQLFactory
{
  public static List getList(List c, String ql)
  {
    Query q = new Query();
    try
    {
      q.parse(ql);
      QueryResults qr = q.execute(c);
      return qr.getResults();
    }
    catch (QueryExecutionException e)
    {
      e.printStackTrace();
    }
    catch (QueryParseException e) {
      e.printStackTrace();
    }

    return null;
  }
  
}