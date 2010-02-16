/**
 * Copyright (c) 2009, 2010, Barthelemy Dagenais All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * - The name of the author may not be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package py4j;

/**
 * <p>
 * A ReturnObject wraps a value returned by a method. If the value is a
 * primitive, a primitive wrapper object (e.g., Integer) or a String, the value
 * is kept in the primitiveObject field.
 * </p>
 * 
 * <p>
 * If the return value is an object, a key to the reference is kept in the name
 * field. This value can be retrieved by calling
 * {@link py4j.Gateway#getObject(String)} with the key.
 * </p>
 * 
 * <p>
 * Various methods are defined to determine the type of the return value. For
 * example, if the return value is void, the name and primitiveObject fields are
 * null, but {@link #isVoid()} returns true.
 * </p>
 * 
 * <p>
 * ReturnObject objects can only be constructed through static factory methods
 * such as {@link #getListReturnObject(String, int)}.
 * </p>
 * 
 * @author barthelemy
 * 
 */
public class ReturnObject {

	private String name;
	private Object primitiveObject;
	private boolean isReference;
	private boolean isMap;
	private boolean isList;
	private boolean isNull;
	private boolean isError;
	private boolean isVoid;
	private int size;

	private ReturnObject() {
	}

	public static ReturnObject getMapReturnObject(String name, int size) {
		ReturnObject rObject = new ReturnObject();
		rObject.name = name;
		rObject.size = size;
		rObject.isMap = true;
		return rObject;
	}
	
	public static ReturnObject getListReturnObject(String name, int size) {
		ReturnObject rObject = new ReturnObject();
		rObject.name = name;
		rObject.size = size;
		rObject.isList = true;
		return rObject;
	}

	public static ReturnObject getPrimitiveReturnObject(Object primitive) {
		ReturnObject rObject = new ReturnObject();
		rObject.primitiveObject = primitive;
		return rObject;
	}

	public static ReturnObject getReferenceReturnObject(String name) {
		ReturnObject rObject = new ReturnObject();
		rObject.name = name;
		rObject.isReference = true;
		return rObject;
	}

	public static ReturnObject getNullReturnObject() {
		ReturnObject rObject = new ReturnObject();
		rObject.isNull = true;
		return rObject;
	}

	public static ReturnObject getVoidReturnObject() {
		ReturnObject rObject = new ReturnObject();
		rObject.isVoid = true;
		return rObject;
	}

	public static ReturnObject getErrorReturnObject() {
		ReturnObject rObject = new ReturnObject();
		rObject.isError = true;
		return rObject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMap() {
		return isMap;
	}

	public void setMap(boolean isMap) {
		this.isMap = isMap;
	}

	public boolean isList() {
		return isList;
	}

	public void setList(boolean isList) {
		this.isList = isList;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public Object getPrimitiveObject() {
		return primitiveObject;
	}

	public void setPrimitiveObject(Object primitiveObject) {
		this.primitiveObject = primitiveObject;
	}

	public boolean isReference() {
		return isReference;
	}

	public void setReference(boolean isReference) {
		this.isReference = isReference;
	}

	public boolean isVoid() {
		return isVoid;
	}

	public void setVoid(boolean isVoid) {
		this.isVoid = isVoid;
	}

}