package com.stream.fetcher;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CssSelector {
    private static Object selectData(String value, Object elementData) throws  Exception{
        if(elementData instanceof Document)
            return ((Document)elementData).select(value);
        else if(elementData instanceof Elements)
            return ((Elements)elementData).select(value);
        else if(elementData instanceof Element)
            return ((Element)elementData).select(value);
        else
            throw new Exception("Unidentified data");
    }

    private static Object attrData(String value, Object elementData) throws  Exception{
        if(elementData instanceof Document)
            return ((Document)elementData).attr(value);
        else if(elementData instanceof Elements)
            return ((Elements)elementData).attr(value);
        else if(elementData instanceof Element)
            return ((Element)elementData).attr(value);
        else
            throw new Exception("Unidentified data");
    }

    private static Object textData(String value, Object elementData) throws  Exception{
        if(elementData instanceof Document)
            return ((Document)elementData).text();
        else if(elementData instanceof Elements)
            return ((Elements)elementData).text();
        else if(elementData instanceof Element)
            return ((Element)elementData).text();
        else
            throw new Exception("Unidentified data");
    }

    private  static Object firstData(String value, Object elementData) throws  Exception{
        if(elementData instanceof Elements)
            return ((Elements)elementData).first();
        else
            throw new Exception("Unidentified data");
    }

    private static Object absUrlData(String value, Object elementData) throws  Exception{
        if(elementData instanceof Document)
            return ((Document)elementData).absUrl(value);
        else if(elementData instanceof Element)
            return ((Element)elementData).absUrl(value);
        else
            throw new Exception("Unidentified data ");
    }

    private static Object indexData(String value, Object elementData) throws  Exception{
        int index = Integer.parseInt(value);
        if(elementData instanceof Elements)
            return ((Elements)elementData).get(index);
        else
            throw new Exception("Unidentified data");
    }

    public static Object getData(String key, String value, Object elementData) throws Exception{
        key = key.split("--")[0];
        switch (key){
            case "select":
                return selectData(value, elementData);
            case "attr":
                return attrData(value, elementData);
            case "text":
                return textData(value, elementData);
            case "first":
                return firstData(value, elementData);
            case "absUrl":
                return absUrlData(value, elementData);
            case "index":
                return indexData(value, elementData);
            default:
                throw new Exception("Unidentified type");
        }
    }
}
