/**
 * @Copyright base
 */
package com.base.common.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author hhx
 *
 */
public class CharsetUtil {
    private static Logger LOG = Logger.getLogger(CharsetUtil.class);
    /**
     * 判断是否为BIG5字符
     * 
     * @param head
     * @param tail
     * @return
     */
    public static boolean isBIG5( byte head, byte tail )
    {
        int iHead = head & 0xff;
        int iTail = tail & 0xff;
        /*return ((iHead>=0xa1 && iHead<=0xf9 &&  
               (iTail>=0x40 && iTail<=0x7e)||  
               iTail>=0xa1 && iTail<=0xfe)) ? true : false);*/
        return ( (iHead>=0x81 && iHead<=0xfe)&&
                ( (iTail>=0x40 && iTail<=0x7e)||
                   (iTail>=0xa1 && iTail<=0xfe)))?true:false;
    }
   
    /**
     * 判断是否为GB2312字符
     * 
     * @param head
     * @param tail
     * @return
     */
    public static boolean isGB2312( byte head,byte tail )
    {  
        int iHead = head & 0xff;  
        int iTail = tail & 0xff;  
        /*return ((iHead>=0xa1 && iHead<=0xf7 &&   
                 iTail>=0xa1 && iTail<=0xfe) ? true : false);*/
        return ((iHead>=0xb0 && iHead<=0xf7)&&
                (iTail>=0xa0 && iTail<=0xfe))?true:false;
    }
   
    /**
     * 判断是否为GB18030字符
     * 
     * @param head
     * @param tail
     * @return
     */
    public static boolean isGB18030( byte head,byte tail )
    {  
        int iHead = head & 0xff;  
        int iTail = tail & 0xff;  
        return ((iHead>=0x81 && iHead<=0xFE &&
                ( (iTail>=0x40 && iTail<=0x7E)||
                (iTail>=0x80 && iTail<=0xFE) )) ? true : false);  
    }  
   
    /**
     * 判断是否为GBK字符
     * 
     * @param head
     * @param tail
     * @return
     */
    public static boolean isGBK( byte head,byte tail )
    {  
        int iHead = head & 0xff;  
        int iTail = tail & 0xff;  
        /*return ((iHead>=0x81 && iHead<=0xfe &&   
                 (iTail>=0x40 && iTail<=0x7e ||   
                  iTail>=0x80 && iTail<=0xfe)) ? true : false);*/
        return ((iHead>=0x81 && iHead<=0xfe)&&
                (iTail>=0x40 && iTail<=0xfe))?true:false;
    }
   
    /**
     * 判断一个ISO8859-1字符串是否采用BIG5字符集
     * @param line
     * @return
     */
    public static int isBIG5( String line )
    {
        int count=0;
        for( int i=0;i<line.length()-1;i++ )
        {
            if( isBIG5( (byte)line.charAt(i), (byte)line.charAt(i+1) ) )
            {
                count++;
                i++;//跳过一个字符
            }
        }
        return count;
    }
   
    /**
     * 判断一个ISO8859-1字符串是否采用GB2312字符集
     * @param line
     * @return
     */
    public static int isGB2312( String line )
    {
        int count=0;
        for( int i=0;i<line.length()-1;i++ )
        {
            if( isGB2312( (byte)line.charAt(i), (byte)line.charAt(i+1) ) )
            {
                count++;
                i++;//跳过一个字符
            }
        }
        return count;
    }
   
    /**
     * 判断一个ISO8859-1字符串是否采用GBK字符集
     * @param line
     * @return
     */
    public static int isGBK( String line )
    {
        int count=0;
        for( int i=0;i<line.length()-1;i++ )
        {
            if( isGBK( (byte)line.charAt(i), (byte)line.charAt(i+1) ) )
            {
                count++;
                i++;//跳过一个字符
            }
        }
        return count;
    }
   
    /**
     * 判断一个ISO8859-1字符串是否采用GB18030字符集
     * @param line
     * @return
     */
    public static int isGB18030( String line )
    {
        int count=0;
        for( int i=0;i<line.length()-1;i++ )
        {
            if( isGB18030( (byte)line.charAt(i), (byte)line.charAt(i+1) ) )
            {
                count++;
                i++;//跳过一个字符
            }
        }
        return count;
    }
    
    /**
     * 判断输入编码
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static String getInputEncoding(InputStream in) throws IOException {
        in.mark(-1);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        String encoding = null;
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (isGB18030(line) > 0) {
                encoding = "GB18030";
                break;
            }
            
            if (isGBK(line) > 0) {
                encoding = "GBK";
                break;
            }
            
            if (isGB2312(line) > 0) {
                encoding = "GBK";
                break;
            }
            
            if (isBIG5(line) > 0) {
                encoding = "GBK";
                break;
            }
        }
        
        in.reset();
        return encoding;
    }
} 
