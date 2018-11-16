package org.ZeorCopy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBuffZeroCOpy {
	
	
	/*
	 *      for function:transferTo, the file size must is smaller than 2G
			begin time:2018-10-29T08:57:15.620
			end time:2018-10-29T09:02:01.909
			transfer begin time:2018-10-29T09:02:01.909
			transferend time:2018-10-29T09:02:45.206
	 * 
	 * 
	 */
	

	public static void main(String[] args) throws Exception {
		ByteBuffer buff = ByteBuffer.allocate(1024);
        FileChannel fin = null;
        FileChannel fout = null;
        
        //traditional solution
        System.out.println("begin time:"+LocalDateTime.now());
            fin = new FileInputStream("f:\\1.zip").getChannel();
            fout = new FileOutputStream("f:\\2.zip").getChannel();
            while(fin.read(buff) != -1) {
                buff.flip();
                fout.write(buff);
                buff.clear();
            }
        System.out.println("end time:"+LocalDateTime.now());
          
        
        //Zero-copy
        System.out.println("transfer begin time:"+LocalDateTime.now());
        FileChannel fin1=new FileInputStream("f:\\1.zip").getChannel();
        FileChannel fout2 = new FileOutputStream("f:\\3.zip").getChannel();
        System.out.println("file size:"+fin1.size());
        fin1.transferTo(0, fin1.size(), fout2);
        System.out.println("transferend time:"+LocalDateTime.now());
        
        
        
        byte[] bytes = null;
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
	}

}
