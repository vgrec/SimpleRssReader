package com.simplerssreader.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Author vgrec, on 09.07.16.
 */
@NamespaceList({@Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")})
@Root(strict = false)
public class Channel {

    @ElementList(name = "item", required = true, inline = true)
    private List<Item> itemList;

    public List<Item> getItemList() {
        return itemList;
    }

}

