/* see bouncycastle_license.txt */

package com.lowagie.bc.asn1;

import java.util.Vector;

/**
 * a general class for building up a vector of DER encodable objects -
 * this will eventually be superceded by ASN1EncodableVector so you should
 * use that class in preference.
 */
public class DEREncodableVector
{
    private Vector  v = new Vector();

    public void add(
        DEREncodable   obj)
    {
        v.addElement(obj);
    }

    public DEREncodable get(
        int i)
    {
        return (DEREncodable)v.elementAt(i);
    }

    public int size()
    {
        return v.size();
    }
}
