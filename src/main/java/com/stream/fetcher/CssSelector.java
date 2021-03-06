package com.stream.fetcher;

import com.stream.exceptions.BadTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CssSelector {
    private static Object selectData(String value, Object elementData) throws  BadTypeException{
        if(elementData instanceof Document){
            return ((Document)elementData).select(value);
        }
        else if(elementData instanceof Elements) {
            return ((Elements) elementData).select(value);
        }
        else if(elementData instanceof Element){
            return ((Element)elementData).select(value);
        }
        else {
            throw new BadTypeException("Unidentified data");
        }
    }

    private static Object attrData(String value, Object elementData) throws  BadTypeException{
        if(elementData instanceof Document) {
            return ((Document) elementData).attr(value);
        }
        else if(elementData instanceof Elements) {
            return ((Elements) elementData).attr(value);
        }
        else if(elementData instanceof Element) {
            return ((Element) elementData).attr(value);
        }
        else {
            throw new BadTypeException("Unidentified data");
        }
    }

    private static Object textData(Object elementData) throws  BadTypeException{
        if(elementData instanceof Document) {
            return ((Document) elementData).text();
        }
        else if(elementData instanceof Elements) {
            return ((Elements) elementData).text();
        }
        else if(elementData instanceof Element) {
            return ((Element) elementData).text();
        }
        else {
            throw new BadTypeException("Unidentified data");
        }
    }

    private  static Object firstData(Object elementData) throws  BadTypeException{
        if(elementData instanceof Elements) {
            return ((Elements) elementData).first();
        }
        else {
            throw new BadTypeException("Unidentified data");
        }
    }

    private static Object absUrlData(String value, Object elementData) throws  BadTypeException{
        if(elementData instanceof Document) {
            return ((Document) elementData).absUrl(value);
        }
        else if(elementData instanceof Element) {
            return ((Element) elementData).absUrl(value);
        }
        else {
            throw new BadTypeException("Unidentified data ");
        }
    }

    private static Object indexData(String value, Object elementData) throws  BadTypeException{
        int index = Integer.parseInt(value);
        if(elementData instanceof Elements) {
            return ((Elements) elementData).get(index);
        }
        else {
            throw new BadTypeException("Unidentified data");
        }
    }

    public static Object getData(String key, String value, Object elementData) throws BadTypeException{
        String newKey = key.split("--")[0];
        switch (newKey){
            case "select":
                return selectData(value, elementData);
            case "attr":
                return attrData(value, elementData);
            case "text":
                return textData(elementData);
            case "first":
                return firstData(elementData);
            case "absUrl":
                return absUrlData(value, elementData);
            case "index":
                return indexData(value, elementData);
            default:
                throw new BadTypeException("Unidentified type");
        }
    }
}
