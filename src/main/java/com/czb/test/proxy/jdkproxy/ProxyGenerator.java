//package com.czb.test.proxy;
//
//
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Array;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.nio.file.Files;
//import java.nio.file.OpenOption;
//import java.nio.file.Path;
//import java.security.AccessController;
//import java.security.PrivilegedAction;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.Map;
//import sun.security.action.GetBooleanAction;
//
//public class ProxyGenerator {
//    private static final int CLASSFILE_MAJOR_VERSION = 49;
//    private static final int CLASSFILE_MINOR_VERSION = 0;
//    private static final int CONSTANT_UTF8 = 1;
//    private static final int CONSTANT_UNICODE = 2;
//    private static final int CONSTANT_INTEGER = 3;
//    private static final int CONSTANT_FLOAT = 4;
//    private static final int CONSTANT_LONG = 5;
//    private static final int CONSTANT_DOUBLE = 6;
//    private static final int CONSTANT_CLASS = 7;
//    private static final int CONSTANT_STRING = 8;
//    private static final int CONSTANT_FIELD = 9;
//    private static final int CONSTANT_METHOD = 10;
//    private static final int CONSTANT_INTERFACEMETHOD = 11;
//    private static final int CONSTANT_NAMEANDTYPE = 12;
//    private static final int ACC_PUBLIC = 1;
//    private static final int ACC_PRIVATE = 2;
//    private static final int ACC_STATIC = 8;
//    private static final int ACC_FINAL = 16;
//    private static final int ACC_SUPER = 32;
//    private static final int opc_aconst_null = 1;
//    private static final int opc_iconst_0 = 3;
//    private static final int opc_bipush = 16;
//    private static final int opc_sipush = 17;
//    private static final int opc_ldc = 18;
//    private static final int opc_ldc_w = 19;
//    private static final int opc_iload = 21;
//    private static final int opc_lload = 22;
//    private static final int opc_fload = 23;
//    private static final int opc_dload = 24;
//    private static final int opc_aload = 25;
//    private static final int opc_iload_0 = 26;
//    private static final int opc_lload_0 = 30;
//    private static final int opc_fload_0 = 34;
//    private static final int opc_dload_0 = 38;
//    private static final int opc_aload_0 = 42;
//    private static final int opc_astore = 58;
//    private static final int opc_astore_0 = 75;
//    private static final int opc_aastore = 83;
//    private static final int opc_pop = 87;
//    private static final int opc_dup = 89;
//    private static final int opc_ireturn = 172;
//    private static final int opc_lreturn = 173;
//    private static final int opc_freturn = 174;
//    private static final int opc_dreturn = 175;
//    private static final int opc_areturn = 176;
//    private static final int opc_return = 177;
//    private static final int opc_getstatic = 178;
//    private static final int opc_putstatic = 179;
//    private static final int opc_getfield = 180;
//    private static final int opc_invokevirtual = 182;
//    private static final int opc_invokespecial = 183;
//    private static final int opc_invokestatic = 184;
//    private static final int opc_invokeinterface = 185;
//    private static final int opc_new = 187;
//    private static final int opc_anewarray = 189;
//    private static final int opc_athrow = 191;
//    private static final int opc_checkcast = 192;
//    private static final int opc_wide = 196;
//    private static final String superclassName = "java/lang/reflect/Proxy";
//    private static final String handlerFieldName = "h";
//    private static final boolean saveGeneratedFiles = (Boolean)AccessController.doPrivileged(new GetBooleanAction("jdk.proxy.ProxyGenerator.saveGeneratedFiles"));
//    private static Method hashCodeMethod;
//    private static Method equalsMethod;
//    private static Method toStringMethod;
//    private String className;
//    private Class<?>[] interfaces;
//    private int accessFlags;
//    private ConstantPool cp = new ProxyGenerator.ConstantPool();
//    private List<ProxyGenerator.FieldInfo> fields = new ArrayList();
//    private List<ProxyGenerator.MethodInfo> methods = new ArrayList();
//    private Map<String, List<ProxyGenerator.ProxyMethod>> proxyMethods = new HashMap();
//    private int proxyMethodCount = 0;
//
//    static byte[] generateProxyClass(String name, Class<?>[] interfaces) {
//        return generateProxyClass(name, interfaces, 49);
//    }
//
//    static byte[] generateProxyClass(final String name, Class<?>[] interfaces, int accessFlags) {
//        ProxyGenerator gen = new ProxyGenerator(name, interfaces, accessFlags);
//        final byte[] classFile = gen.generateClassFile();
//        if (saveGeneratedFiles) {
//            AccessController.doPrivileged(new PrivilegedAction<Void>() {
//                public Void run() {
//                    try {
//                        int i = name.lastIndexOf(46);
//                        Path path;
//                        if (i > 0) {
//                            Path dir = Path.of(name.substring(0, i).replace('.', File.separatorChar));
//                            Files.createDirectories(dir);
//                            path = dir.resolve(name.substring(i + 1, name.length()) + ".class");
//                        } else {
//                            path = Path.of(name + ".class");
//                        }
//
//                        Files.write(path, classFile, new OpenOption[0]);
//                        return null;
//                    } catch (IOException var4) {
//                        throw new InternalError("I/O exception saving generated file: " + var4);
//                    }
//                }
//            });
//        }
//
//        return classFile;
//    }
//
//    private ProxyGenerator(String className, Class<?>[] interfaces, int accessFlags) {
//        this.className = className;
//        this.interfaces = interfaces;
//        this.accessFlags = accessFlags;
//    }
//
//    private byte[] generateClassFile() {
//        this.addProxyMethod(hashCodeMethod, Object.class);
//        this.addProxyMethod(equalsMethod, Object.class);
//        this.addProxyMethod(toStringMethod, Object.class);
//        Class[] var1 = this.interfaces;
//        int var2 = var1.length;
//
//        int var3;
//        Class intf;
//        for(var3 = 0; var3 < var2; ++var3) {
//            intf = var1[var3];
//            Method[] var5 = intf.getMethods();
//            int var6 = var5.length;
//
//            for(int var7 = 0; var7 < var6; ++var7) {
//                Method m = var5[var7];
//                if (!Modifier.isStatic(m.getModifiers())) {
//                    this.addProxyMethod(m, intf);
//                }
//            }
//        }
//
//        Iterator var11 = this.proxyMethods.values().iterator();
//
//        List sigmethods;
//        while(var11.hasNext()) {
//            sigmethods = (List)var11.next();
//            checkReturnTypes(sigmethods);
//        }
//
//        Iterator var15;
//        try {
//            this.methods.add(this.generateConstructor());
//            var11 = this.proxyMethods.values().iterator();
//
//            while(var11.hasNext()) {
//                sigmethods = (List)var11.next();
//                var15 = sigmethods.iterator();
//
//                while(var15.hasNext()) {
//                    ProxyGenerator.ProxyMethod pm = (ProxyGenerator.ProxyMethod)var15.next();
//                    this.fields.add(new ProxyGenerator.FieldInfo(pm.methodFieldName, "Ljava/lang/reflect/Method;", 10));
//                    this.methods.add(pm.generateMethod());
//                }
//            }
//
//            this.methods.add(this.generateStaticInitializer());
//        } catch (IOException var10) {
//            throw new InternalError("unexpected I/O Exception", var10);
//        }
//
//        if (this.methods.size() > 65535) {
//            throw new IllegalArgumentException("method limit exceeded");
//        } else if (this.fields.size() > 65535) {
//            throw new IllegalArgumentException("field limit exceeded");
//        } else {
//            this.cp.getClass(dotToSlash(this.className));
//            this.cp.getClass("java/lang/reflect/Proxy");
//            var1 = this.interfaces;
//            var2 = var1.length;
//
//            for(var3 = 0; var3 < var2; ++var3) {
//                intf = var1[var3];
//                this.cp.getClass(dotToSlash(intf.getName()));
//            }
//
//            this.cp.setReadOnly();
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            DataOutputStream dout = new DataOutputStream(bout);
//
//            try {
//                dout.writeInt(-889275714);
//                dout.writeShort(0);
//                dout.writeShort(49);
//                this.cp.write(dout);
//                dout.writeShort(this.accessFlags);
//                dout.writeShort(this.cp.getClass(dotToSlash(this.className)));
//                dout.writeShort(this.cp.getClass("java/lang/reflect/Proxy"));
//                dout.writeShort(this.interfaces.length);
//                Class[] var17 = this.interfaces;
//                int var18 = var17.length;
//
//                for(int var19 = 0; var19 < var18; ++var19) {
//                    Class<?> intf1 = var17[var19];
//                    dout.writeShort(this.cp.getClass(dotToSlash(intf1.getName())));
//                }
//
//                dout.writeShort(this.fields.size());
//                var15 = this.fields.iterator();
//
//                while(var15.hasNext()) {
//                    ProxyGenerator.FieldInfo f = (ProxyGenerator.FieldInfo)var15.next();
//                    f.write(dout);
//                }
//
//                dout.writeShort(this.methods.size());
//                var15 = this.methods.iterator();
//
//                while(var15.hasNext()) {
//                    ProxyGenerator.MethodInfo m = (ProxyGenerator.MethodInfo)var15.next();
//                    m.write(dout);
//                }
//
//                dout.writeShort(0);
//                return bout.toByteArray();
//            } catch (IOException var9) {
//                throw new InternalError("unexpected I/O Exception", var9);
//            }
//        }
//    }
//
//    private void addProxyMethod(Method m, Class<?> fromClass) {
//        String name = m.getName();
//        Class<?>[] parameterTypes = m.getParameterTypes();
//        Class<?> returnType = m.getReturnType();
//        Class<?>[] exceptionTypes = m.getExceptionTypes();
//        String sig = name + getParameterDescriptors(parameterTypes);
//        List<ProxyGenerator.ProxyMethod> sigmethods = (List)this.proxyMethods.get(sig);
//        if (sigmethods != null) {
//            Iterator var9 = ((List)sigmethods).iterator();
//
//            while(var9.hasNext()) {
//                ProxyGenerator.ProxyMethod pm = (ProxyGenerator.ProxyMethod)var9.next();
//                if (returnType == pm.returnType) {
//                    List<Class<?>> legalExceptions = new ArrayList();
//                    collectCompatibleTypes(exceptionTypes, pm.exceptionTypes, legalExceptions);
//                    collectCompatibleTypes(pm.exceptionTypes, exceptionTypes, legalExceptions);
//                    pm.exceptionTypes = new Class[legalExceptions.size()];
//                    pm.exceptionTypes = (Class[])legalExceptions.toArray(pm.exceptionTypes);
//                    return;
//                }
//            }
//        } else {
//            sigmethods = new ArrayList(3);
//            this.proxyMethods.put(sig, sigmethods);
//        }
//
//        ((List)sigmethods).add(new ProxyGenerator.ProxyMethod(name, parameterTypes, returnType, exceptionTypes, fromClass));
//    }
//
//    private static void checkReturnTypes(List<ProxyGenerator.ProxyMethod> methods) {
//        if (methods.size() >= 2) {
//            LinkedList<Class<?>> uncoveredReturnTypes = new LinkedList();
//            Iterator var2 = methods.iterator();
//
//            boolean added;
//            label49:
//            do {
//                while(var2.hasNext()) {
//                    ProxyGenerator.ProxyMethod pm = (ProxyGenerator.ProxyMethod)var2.next();
//                    Class<?> newReturnType = pm.returnType;
//                    if (newReturnType.isPrimitive()) {
//                        throw new IllegalArgumentException("methods with same signature " + getFriendlyMethodSignature(pm.methodName, pm.parameterTypes) + " but incompatible return types: " + newReturnType.getName() + " and others");
//                    }
//
//                    added = false;
//                    ListIterator liter = uncoveredReturnTypes.listIterator();
//
//                    while(liter.hasNext()) {
//                        Class<?> uncoveredReturnType = (Class)liter.next();
//                        if (newReturnType.isAssignableFrom(uncoveredReturnType)) {
//                            continue label49;
//                        }
//
//                        if (uncoveredReturnType.isAssignableFrom(newReturnType)) {
//                            if (!added) {
//                                liter.set(newReturnType);
//                                added = true;
//                            } else {
//                                liter.remove();
//                            }
//                        }
//                    }
//
//                    if (!added) {
//                        uncoveredReturnTypes.add(newReturnType);
//                    }
//                }
//
//                if (uncoveredReturnTypes.size() > 1) {
//                    ProxyGenerator.ProxyMethod pm = (ProxyGenerator.ProxyMethod)methods.get(0);
//                    throw new IllegalArgumentException("methods with same signature " + getFriendlyMethodSignature(pm.methodName, pm.parameterTypes) + " but incompatible return types: " + uncoveredReturnTypes);
//                }
//
//                return;
//            } while( !added);
//
//            throw new AssertionError();
//        }
//    }
//
//    private ProxyGenerator.MethodInfo generateConstructor() throws IOException {
//        ProxyGenerator.MethodInfo minfo = new ProxyGenerator.MethodInfo("<init>", "(Ljava/lang/reflect/InvocationHandler;)V", 1);
//        DataOutputStream out = new DataOutputStream(minfo.code);
//        this.code_aload(0, out);
//        this.code_aload(1, out);
//        out.writeByte(183);
//        out.writeShort(this.cp.getMethodRef("java/lang/reflect/Proxy", "<init>", "(Ljava/lang/reflect/InvocationHandler;)V"));
//        out.writeByte(177);
//        minfo.maxStack = 10;
//        minfo.maxLocals = 2;
//        minfo.declaredExceptions = new short[0];
//        return minfo;
//    }
//
//    private ProxyGenerator.MethodInfo generateStaticInitializer() throws IOException {
//        ProxyGenerator.MethodInfo minfo = new ProxyGenerator.MethodInfo("<clinit>", "()V", 8);
//        int localSlot0 = 1;
//        short tryBegin = 0;
//        DataOutputStream out = new DataOutputStream(minfo.code);
//        Iterator var7 = this.proxyMethods.values().iterator();
//
//        while(var7.hasNext()) {
//            List<ProxyGenerator.ProxyMethod> sigmethods = (List)var7.next();
//            Iterator var9 = sigmethods.iterator();
//
//            while(var9.hasNext()) {
//                ProxyGenerator.ProxyMethod pm = (ProxyGenerator.ProxyMethod)var9.next();
//                pm.codeFieldInitialization(out);
//            }
//        }
//
//        out.writeByte(177);
//        short pc;
//        short tryEnd = pc = (short)minfo.code.size();
//        minfo.exceptionTable.add(new ProxyGenerator.ExceptionTableEntry(tryBegin, tryEnd, pc, this.cp.getClass("java/lang/NoSuchMethodException")));
//        this.code_astore(localSlot0, out);
//        out.writeByte(187);
//        out.writeShort(this.cp.getClass("java/lang/NoSuchMethodError"));
//        out.writeByte(89);
//        this.code_aload(localSlot0, out);
//        out.writeByte(182);
//        out.writeShort(this.cp.getMethodRef("java/lang/Throwable", "getMessage", "()Ljava/lang/String;"));
//        out.writeByte(183);
//        out.writeShort(this.cp.getMethodRef("java/lang/NoSuchMethodError", "<init>", "(Ljava/lang/String;)V"));
//        out.writeByte(191);
//        pc = (short)minfo.code.size();
//        minfo.exceptionTable.add(new ProxyGenerator.ExceptionTableEntry(tryBegin, tryEnd, pc, this.cp.getClass("java/lang/ClassNotFoundException")));
//        this.code_astore(localSlot0, out);
//        out.writeByte(187);
//        out.writeShort(this.cp.getClass("java/lang/NoClassDefFoundError"));
//        out.writeByte(89);
//        this.code_aload(localSlot0, out);
//        out.writeByte(182);
//        out.writeShort(this.cp.getMethodRef("java/lang/Throwable", "getMessage", "()Ljava/lang/String;"));
//        out.writeByte(183);
//        out.writeShort(this.cp.getMethodRef("java/lang/NoClassDefFoundError", "<init>", "(Ljava/lang/String;)V"));
//        out.writeByte(191);
//        if (minfo.code.size() > 65535) {
//            throw new IllegalArgumentException("code size limit exceeded");
//        } else {
//            minfo.maxStack = 10;
//            minfo.maxLocals = (short)(localSlot0 + 1);
//            minfo.declaredExceptions = new short[0];
//            return minfo;
//        }
//    }
//
//    private void code_iload(int lvar, DataOutputStream out) throws IOException {
//        this.codeLocalLoadStore(lvar, 21, 26, out);
//    }
//
//    private void code_lload(int lvar, DataOutputStream out) throws IOException {
//        this.codeLocalLoadStore(lvar, 22, 30, out);
//    }
//
//    private void code_fload(int lvar, DataOutputStream out) throws IOException {
//        this.codeLocalLoadStore(lvar, 23, 34, out);
//    }
//
//    private void code_dload(int lvar, DataOutputStream out) throws IOException {
//        this.codeLocalLoadStore(lvar, 24, 38, out);
//    }
//
//    private void code_aload(int lvar, DataOutputStream out) throws IOException {
//        this.codeLocalLoadStore(lvar, 25, 42, out);
//    }
//
//    private void code_astore(int lvar, DataOutputStream out) throws IOException {
//        this.codeLocalLoadStore(lvar, 58, 75, out);
//    }
//
//    private void codeLocalLoadStore(int lvar, int opcode, int opcode_0, DataOutputStream out) throws IOException {
//        assert lvar >= 0 && lvar <= 65535;
//
//        if (lvar <= 3) {
//            out.writeByte(opcode_0 + lvar);
//        } else if (lvar <= 255) {
//            out.writeByte(opcode);
//            out.writeByte(lvar & 255);
//        } else {
//            out.writeByte(196);
//            out.writeByte(opcode);
//            out.writeShort(lvar & '\uffff');
//        }
//
//    }
//
//    private void code_ldc(int index, DataOutputStream out) throws IOException {
//        assert index >= 0 && index <= 65535;
//
//        if (index <= 255) {
//            out.writeByte(18);
//            out.writeByte(index & 255);
//        } else {
//            out.writeByte(19);
//            out.writeShort(index & '\uffff');
//        }
//
//    }
//
//    private void code_ipush(int value, DataOutputStream out) throws IOException {
//        if (value >= -1 && value <= 5) {
//            out.writeByte(3 + value);
//        } else if (value >= -128 && value <= 127) {
//            out.writeByte(16);
//            out.writeByte(value & 255);
//        } else {
//            if (value < -32768 || value > 32767) {
//                throw new AssertionError();
//            }
//
//            out.writeByte(17);
//            out.writeShort(value & '\uffff');
//        }
//
//    }
//
//    private void codeClassForName(Class<?> cl, DataOutputStream out) throws IOException {
//        this.code_ldc(this.cp.getString(cl.getName()), out);
//        out.writeByte(184);
//        out.writeShort(this.cp.getMethodRef("java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
//    }
//
//    private static String dotToSlash(String name) {
//        return name.replace('.', '/');
//    }
//
//    private static String getMethodDescriptor(Class<?>[] parameterTypes, Class<?> returnType) {
//        return getParameterDescriptors(parameterTypes) + (returnType == Void.TYPE ? "V" : getFieldType(returnType));
//    }
//
//    private static String getParameterDescriptors(Class<?>[] parameterTypes) {
//        StringBuilder desc = new StringBuilder("(");
//
//        for(int i = 0; i < parameterTypes.length; ++i) {
//            desc.append(getFieldType(parameterTypes[i]));
//        }
//
//        desc.append(')');
//        return desc.toString();
//    }
//
//    private static String getFieldType(Class<?> type) {
//        if (type.isPrimitive()) {
//            return ProxyGenerator.PrimitiveTypeInfo.get(type).baseTypeString;
//        } else {
//            return type.isArray() ? type.getName().replace('.', '/') : "L" + dotToSlash(type.getName()) + ";";
//        }
//    }
//
//    private static String getFriendlyMethodSignature(String name, Class<?>[] parameterTypes) {
//        StringBuilder sig = new StringBuilder(name);
//        sig.append('(');
//
//        for(int i = 0; i < parameterTypes.length; ++i) {
//            if (i > 0) {
//                sig.append(',');
//            }
//
//            Class<?> parameterType = parameterTypes[i];
//
//            int var5;
//            for(var5 = 0; parameterType.isArray(); ++var5) {
//                parameterType = parameterType.getComponentType();
//            }
//
//            sig.append(parameterType.getName());
//
//            while(var5-- > 0) {
//                sig.append("[]");
//            }
//        }
//
//        sig.append(')');
//        return sig.toString();
//    }
//
//    private static int getWordsPerType(Class<?> type) {
//        return type != Long.TYPE && type != Double.TYPE ? 1 : 2;
//    }
//
//    private static void collectCompatibleTypes(Class<?>[] from, Class<?>[] with, List<Class<?>> list) {
//        Class[] var3 = from;
//        int var4 = from.length;
//
//        for(int var5 = 0; var5 < var4; ++var5) {
//            Class<?> fc = var3[var5];
//            if (!list.contains(fc)) {
//                Class[] var7 = with;
//                int var8 = with.length;
//
//                for(int var9 = 0; var9 < var8; ++var9) {
//                    Class<?> wc = var7[var9];
//                    if (wc.isAssignableFrom(fc)) {
//                        list.add(fc);
//                        break;
//                    }
//                }
//            }
//        }
//
//    }
//
//    private static List<Class<?>> computeUniqueCatchList(Class<?>[] exceptions) {
//        List<Class<?>> uniqueList = new ArrayList();
//        uniqueList.add(Error.class);
//        uniqueList.add(RuntimeException.class);
//        Class[] var2 = exceptions;
//        int var3 = exceptions.length;
//
//        label36:
//        for(int var4 = 0; var4 < var3; ++var4) {
//            Class<?> ex = var2[var4];
//            if (ex.isAssignableFrom(Throwable.class)) {
//                uniqueList.clear();
//                break;
//            }
//
//            if (Throwable.class.isAssignableFrom(ex)) {
//                int j = 0;
//
//                while(j < uniqueList.size()) {
//                    Class<?> ex2 = (Class)uniqueList.get(j);
//                    if (ex2.isAssignableFrom(ex)) {
//                        continue label36;
//                    }
//
//                    if (ex.isAssignableFrom(ex2)) {
//                        uniqueList.remove(j);
//                    } else {
//                        ++j;
//                    }
//                }
//
//                uniqueList.add(ex);
//            }
//        }
//
//        return uniqueList;
//    }
//
//    static {
//        try {
//            hashCodeMethod = Object.class.getMethod("hashCode");
//            equalsMethod = Object.class.getMethod("equals", Object.class);
//            toStringMethod = Object.class.getMethod("toString");
//        } catch (NoSuchMethodException var1) {
//            throw new NoSuchMethodError(var1.getMessage());
//        }
//    }
//
//    private static class ConstantPool {
//        private List<ProxyGenerator.ConstantPool.Entry> pool = new ArrayList(32);
//        private Map<Object, Integer> map = new HashMap(16);
//        private boolean readOnly = false;
//
//        private ConstantPool() {
//        }
//
//        public short getUtf8(String s) {
//            if (s == null) {
//                throw new NullPointerException();
//            } else {
//                return this.getValue(s);
//            }
//        }
//
//        public short getInteger(int i) {
//            return this.getValue(i);
//        }
//
//        public short getFloat(float f) {
//            return this.getValue(f);
//        }
//
//        public short getClass(String name) {
//            short utf8Index = this.getUtf8(name);
//            return this.getIndirect(new ProxyGenerator.ConstantPool.IndirectEntry(7, utf8Index));
//        }
//
//        public short getString(String s) {
//            short utf8Index = this.getUtf8(s);
//            return this.getIndirect(new ProxyGenerator.ConstantPool.IndirectEntry(8, utf8Index));
//        }
//
//        public short getFieldRef(String className, String name, String descriptor) {
//            short classIndex = this.getClass(className);
//            short nameAndTypeIndex = this.getNameAndType(name, descriptor);
//            return this.getIndirect(new ProxyGenerator.ConstantPool.IndirectEntry(9, classIndex, nameAndTypeIndex));
//        }
//
//        public short getMethodRef(String className, String name, String descriptor) {
//            short classIndex = this.getClass(className);
//            short nameAndTypeIndex = this.getNameAndType(name, descriptor);
//            return this.getIndirect(new ProxyGenerator.ConstantPool.IndirectEntry(10, classIndex, nameAndTypeIndex));
//        }
//
//        public short getInterfaceMethodRef(String className, String name, String descriptor) {
//            short classIndex = this.getClass(className);
//            short nameAndTypeIndex = this.getNameAndType(name, descriptor);
//            return this.getIndirect(new ProxyGenerator.ConstantPool.IndirectEntry(11, classIndex, nameAndTypeIndex));
//        }
//
//        public short getNameAndType(String name, String descriptor) {
//            short nameIndex = this.getUtf8(name);
//            short descriptorIndex = this.getUtf8(descriptor);
//            return this.getIndirect(new ProxyGenerator.ConstantPool.IndirectEntry(12, nameIndex, descriptorIndex));
//        }
//
//        public void setReadOnly() {
//            this.readOnly = true;
//        }
//
//        public void write(OutputStream out) throws IOException {
//            DataOutputStream dataOut = new DataOutputStream(out);
//            dataOut.writeShort(this.pool.size() + 1);
//            Iterator var3 = this.pool.iterator();
//
//            while(var3.hasNext()) {
//                ProxyGenerator.ConstantPool.Entry e = (ProxyGenerator.ConstantPool.Entry)var3.next();
//                e.write(dataOut);
//            }
//
//        }
//
//        private short addEntry(ProxyGenerator.ConstantPool.Entry entry) {
//            this.pool.add(entry);
//            if (this.pool.size() >= 65535) {
//                throw new IllegalArgumentException("constant pool size limit exceeded");
//            } else {
//                return (short)this.pool.size();
//            }
//        }
//
//        private short getValue(Object key) {
//            Integer index = (Integer)this.map.get(key);
//            if (index != null) {
//                return index.shortValue();
//            } else if (this.readOnly) {
//                throw new InternalError("late constant pool addition: " + key);
//            } else {
//                short i = this.addEntry(new ProxyGenerator.ConstantPool.ValueEntry(key));
//                this.map.put(key, Integer.valueOf(i));
//                return i;
//            }
//        }
//
//        private short getIndirect(ProxyGenerator.ConstantPool.IndirectEntry e) {
//            Integer index = (Integer)this.map.get(e);
//            if (index != null) {
//                return index.shortValue();
//            } else if (this.readOnly) {
//                throw new InternalError("late constant pool addition");
//            } else {
//                short i = this.addEntry(e);
//                this.map.put(e, Integer.valueOf(i));
//                return i;
//            }
//        }
//
//        private static class IndirectEntry extends ProxyGenerator.ConstantPool.Entry {
//            private int tag;
//            private short index0;
//            private short index1;
//
//            public IndirectEntry(int tag, short index) {
//                this.tag = tag;
//                this.index0 = index;
//                this.index1 = 0;
//            }
//
//            public IndirectEntry(int tag, short index0, short index1) {
//                this.tag = tag;
//                this.index0 = index0;
//                this.index1 = index1;
//            }
//
//            public void write(DataOutputStream out) throws IOException {
//                out.writeByte(this.tag);
//                out.writeShort(this.index0);
//                if (this.tag == 9 || this.tag == 10 || this.tag == 11 || this.tag == 12) {
//                    out.writeShort(this.index1);
//                }
//
//            }
//
//            public int hashCode() {
//                return this.tag + this.index0 + this.index1;
//            }
//
//            public boolean equals(Object obj) {
//                if (obj instanceof ProxyGenerator.ConstantPool.IndirectEntry) {
//                    ProxyGenerator.ConstantPool.IndirectEntry other = (ProxyGenerator.ConstantPool.IndirectEntry)obj;
//                    if (this.tag == other.tag && this.index0 == other.index0 && this.index1 == other.index1) {
//                        return true;
//                    }
//                }
//
//                return false;
//            }
//        }
//
//        private static class ValueEntry extends ProxyGenerator.ConstantPool.Entry {
//            private Object value;
//
//            public ValueEntry(Object value) {
//                this.value = value;
//            }
//
//            public void write(DataOutputStream out) throws IOException {
//                if (this.value instanceof String) {
//                    out.writeByte(1);
//                    out.writeUTF((String)this.value);
//                } else if (this.value instanceof Integer) {
//                    out.writeByte(3);
//                    out.writeInt((Integer)this.value);
//                } else if (this.value instanceof Float) {
//                    out.writeByte(4);
//                    out.writeFloat((Float)this.value);
//                } else if (this.value instanceof Long) {
//                    out.writeByte(5);
//                    out.writeLong((Long)this.value);
//                } else {
//                    if (!(this.value instanceof Double)) {
//                        throw new InternalError("bogus value entry: " + this.value);
//                    }
//
//                    out.writeDouble(6.0D);
//                    out.writeDouble((Double)this.value);
//                }
//
//            }
//        }
//
//        private abstract static class Entry {
//            private Entry() {
//            }
//
//            public abstract void write(DataOutputStream var1) throws IOException;
//        }
//    }
//
//    private static class PrimitiveTypeInfo {
//        public String baseTypeString;
//        public String wrapperClassName;
//        public String wrapperValueOfDesc;
//        public String unwrapMethodName;
//        public String unwrapMethodDesc;
//        private static Map<Class<?>, ProxyGenerator.PrimitiveTypeInfo> table = new HashMap();
//
//        private static void add(Class<?> primitiveClass, Class<?> wrapperClass) {
//            table.put(primitiveClass, new ProxyGenerator.PrimitiveTypeInfo(primitiveClass, wrapperClass));
//        }
//
//        private PrimitiveTypeInfo(Class<?> primitiveClass, Class<?> wrapperClass) {
//            assert primitiveClass.isPrimitive();
//
//            this.baseTypeString = Array.newInstance(primitiveClass, 0).getClass().getName().substring(1);
//            this.wrapperClassName = ProxyGenerator.dotToSlash(wrapperClass.getName());
//            this.wrapperValueOfDesc = "(" + this.baseTypeString + ")L" + this.wrapperClassName + ";";
//            this.unwrapMethodName = primitiveClass.getName() + "Value";
//            this.unwrapMethodDesc = "()" + this.baseTypeString;
//        }
//
//        public static ProxyGenerator.PrimitiveTypeInfo get(Class<?> cl) {
//            return (ProxyGenerator.PrimitiveTypeInfo)table.get(cl);
//        }
//
//        static {
//            add(Byte.TYPE, Byte.class);
//            add(Character.TYPE, Character.class);
//            add(Double.TYPE, Double.class);
//            add(Float.TYPE, Float.class);
//            add(Integer.TYPE, Integer.class);
//            add(Long.TYPE, Long.class);
//            add(Short.TYPE, Short.class);
//            add(Boolean.TYPE, Boolean.class);
//        }
//    }
//
//    private class ProxyMethod {
//        public String methodName;
//        public Class<?>[] parameterTypes;
//        public Class<?> returnType;
//        public Class<?>[] exceptionTypes;
//        public Class<?> fromClass;
//        public String methodFieldName;
//
//        private ProxyMethod(String methodName, Class<?>[] parameterTypes, Class<?> returnType, Class<?>[] exceptionTypes, Class<?> fromClass) {
//            this.methodName = methodName;
//            this.parameterTypes = parameterTypes;
//            this.returnType = returnType;
//            this.exceptionTypes = exceptionTypes;
//            this.fromClass = fromClass;
//            this.methodFieldName = "m" + ProxyGenerator.this.proxyMethodCount++;
//        }
//
//        private ProxyGenerator.MethodInfo generateMethod() throws IOException {
//            String desc = ProxyGenerator.getMethodDescriptor(this.parameterTypes, this.returnType);
//            ProxyGenerator.MethodInfo minfo = ProxyGenerator.this.new MethodInfo(this.methodName, desc, 17);
//            int[] parameterSlot = new int[this.parameterTypes.length];
//            int nextSlot = 1;
//
//            for(int ix = 0; ix < parameterSlot.length; ++ix) {
//                parameterSlot[ix] = nextSlot;
//                nextSlot += ProxyGenerator.getWordsPerType(this.parameterTypes[ix]);
//            }
//
//            short tryBegin = 0;
//            DataOutputStream out = new DataOutputStream(minfo.code);
//            ProxyGenerator.this.code_aload(0, out);
//            out.writeByte(180);
//            out.writeShort(ProxyGenerator.this.cp.getFieldRef("java/lang/reflect/Proxy", "h", "Ljava/lang/reflect/InvocationHandler;"));
//            ProxyGenerator.this.code_aload(0, out);
//            out.writeByte(178);
//            out.writeShort(ProxyGenerator.this.cp.getFieldRef(ProxyGenerator.dotToSlash(ProxyGenerator.this.className), this.methodFieldName, "Ljava/lang/reflect/Method;"));
//            if (this.parameterTypes.length > 0) {
//                ProxyGenerator.this.code_ipush(this.parameterTypes.length, out);
//                out.writeByte(189);
//                out.writeShort(ProxyGenerator.this.cp.getClass("java/lang/Object"));
//
//                for(int ixx = 0; ixx < this.parameterTypes.length; ++ixx) {
//                    out.writeByte(89);
//                    ProxyGenerator.this.code_ipush(ixx, out);
//                    this.codeWrapArgument(this.parameterTypes[ixx], parameterSlot[ixx], out);
//                    out.writeByte(83);
//                }
//            } else {
//                out.writeByte(1);
//            }
//
//            out.writeByte(185);
//            out.writeShort(ProxyGenerator.this.cp.getInterfaceMethodRef("java/lang/reflect/InvocationHandler", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;"));
//            out.writeByte(4);
//            out.writeByte(0);
//            if (this.returnType == Void.TYPE) {
//                out.writeByte(87);
//                out.writeByte(177);
//            } else {
//                this.codeUnwrapReturnValue(this.returnType, out);
//            }
//
//            short pc;
//            short tryEnd = pc = (short)minfo.code.size();
//            List<Class<?>> catchList = ProxyGenerator.computeUniqueCatchList(this.exceptionTypes);
//            if (catchList.size() > 0) {
//                Iterator var11 = catchList.iterator();
//
//                while(var11.hasNext()) {
//                    Class<?> ex = (Class)var11.next();
//                    minfo.exceptionTable.add(new ProxyGenerator.ExceptionTableEntry(tryBegin, tryEnd, pc, ProxyGenerator.this.cp.getClass(ProxyGenerator.dotToSlash(ex.getName()))));
//                }
//
//                out.writeByte(191);
//                pc = (short)minfo.code.size();
//                minfo.exceptionTable.add(new ProxyGenerator.ExceptionTableEntry(tryBegin, tryEnd, pc, ProxyGenerator.this.cp.getClass("java/lang/Throwable")));
//                ProxyGenerator.this.code_astore(nextSlot, out);
//                out.writeByte(187);
//                out.writeShort(ProxyGenerator.this.cp.getClass("java/lang/reflect/UndeclaredThrowableException"));
//                out.writeByte(89);
//                ProxyGenerator.this.code_aload(nextSlot, out);
//                out.writeByte(183);
//                out.writeShort(ProxyGenerator.this.cp.getMethodRef("java/lang/reflect/UndeclaredThrowableException", "<init>", "(Ljava/lang/Throwable;)V"));
//                out.writeByte(191);
//            }
//
//            if (minfo.code.size() > 65535) {
//                throw new IllegalArgumentException("code size limit exceeded");
//            } else {
//                minfo.maxStack = 10;
//                minfo.maxLocals = (short)(nextSlot + 1);
//                minfo.declaredExceptions = new short[this.exceptionTypes.length];
//
//                for(int i = 0; i < this.exceptionTypes.length; ++i) {
//                    minfo.declaredExceptions[i] = ProxyGenerator.this.cp.getClass(ProxyGenerator.dotToSlash(this.exceptionTypes[i].getName()));
//                }
//
//                return minfo;
//            }
//        }
//
//        private void codeWrapArgument(Class<?> type, int slot, DataOutputStream out) throws IOException {
//            if (type.isPrimitive()) {
//                ProxyGenerator.PrimitiveTypeInfo prim = ProxyGenerator.PrimitiveTypeInfo.get(type);
//                if (type != Integer.TYPE && type != Boolean.TYPE && type != Byte.TYPE && type != Character.TYPE && type != Short.TYPE) {
//                    if (type == Long.TYPE) {
//                        ProxyGenerator.this.code_lload(slot, out);
//                    } else if (type == Float.TYPE) {
//                        ProxyGenerator.this.code_fload(slot, out);
//                    } else {
//                        if (type != Double.TYPE) {
//                            throw new AssertionError();
//                        }
//
//                        ProxyGenerator.this.code_dload(slot, out);
//                    }
//                } else {
//                    ProxyGenerator.this.code_iload(slot, out);
//                }
//
//                out.writeByte(184);
//                out.writeShort(ProxyGenerator.this.cp.getMethodRef(prim.wrapperClassName, "valueOf", prim.wrapperValueOfDesc));
//            } else {
//                ProxyGenerator.this.code_aload(slot, out);
//            }
//
//        }
//
//        private void codeUnwrapReturnValue(Class<?> type, DataOutputStream out) throws IOException {
//            if (type.isPrimitive()) {
//                ProxyGenerator.PrimitiveTypeInfo prim = ProxyGenerator.PrimitiveTypeInfo.get(type);
//                out.writeByte(192);
//                out.writeShort(ProxyGenerator.this.cp.getClass(prim.wrapperClassName));
//                out.writeByte(182);
//                out.writeShort(ProxyGenerator.this.cp.getMethodRef(prim.wrapperClassName, prim.unwrapMethodName, prim.unwrapMethodDesc));
//                if (type != Integer.TYPE && type != Boolean.TYPE && type != Byte.TYPE && type != Character.TYPE && type != Short.TYPE) {
//                    if (type == Long.TYPE) {
//                        out.writeByte(173);
//                    } else if (type == Float.TYPE) {
//                        out.writeByte(174);
//                    } else {
//                        if (type != Double.TYPE) {
//                            throw new AssertionError();
//                        }
//
//                        out.writeByte(175);
//                    }
//                } else {
//                    out.writeByte(172);
//                }
//            } else {
//                out.writeByte(192);
//                out.writeShort(ProxyGenerator.this.cp.getClass(ProxyGenerator.dotToSlash(type.getName())));
//                out.writeByte(176);
//            }
//
//        }
//
//        private void codeFieldInitialization(DataOutputStream out) throws IOException {
//            ProxyGenerator.this.codeClassForName(this.fromClass, out);
//            ProxyGenerator.this.code_ldc(ProxyGenerator.this.cp.getString(this.methodName), out);
//            ProxyGenerator.this.code_ipush(this.parameterTypes.length, out);
//            out.writeByte(189);
//            out.writeShort(ProxyGenerator.this.cp.getClass("java/lang/Class"));
//
//            for(int i = 0; i < this.parameterTypes.length; ++i) {
//                out.writeByte(89);
//                ProxyGenerator.this.code_ipush(i, out);
//                if (this.parameterTypes[i].isPrimitive()) {
//                    ProxyGenerator.PrimitiveTypeInfo prim = ProxyGenerator.PrimitiveTypeInfo.get(this.parameterTypes[i]);
//                    out.writeByte(178);
//                    out.writeShort(ProxyGenerator.this.cp.getFieldRef(prim.wrapperClassName, "TYPE", "Ljava/lang/Class;"));
//                } else {
//                    ProxyGenerator.this.codeClassForName(this.parameterTypes[i], out);
//                }
//
//                out.writeByte(83);
//            }
//
//            out.writeByte(182);
//            out.writeShort(ProxyGenerator.this.cp.getMethodRef("java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
//            out.writeByte(179);
//            out.writeShort(ProxyGenerator.this.cp.getFieldRef(ProxyGenerator.dotToSlash(ProxyGenerator.this.className), this.methodFieldName, "Ljava/lang/reflect/Method;"));
//        }
//    }
//
//    private class MethodInfo {
//        public int accessFlags;
//        public String name;
//        public String descriptor;
//        public short maxStack;
//        public short maxLocals;
//        public ByteArrayOutputStream code = new ByteArrayOutputStream();
//        public List<ProxyGenerator.ExceptionTableEntry> exceptionTable = new ArrayList();
//        public short[] declaredExceptions;
//
//        public MethodInfo(String name, String descriptor, int accessFlags) {
//            this.name = name;
//            this.descriptor = descriptor;
//            this.accessFlags = accessFlags;
//            ProxyGenerator.this.cp.getUtf8(name);
//            ProxyGenerator.this.cp.getUtf8(descriptor);
//            ProxyGenerator.this.cp.getUtf8("Code");
//            ProxyGenerator.this.cp.getUtf8("Exceptions");
//        }
//
//        public void write(DataOutputStream out) throws IOException {
//            out.writeShort(this.accessFlags);
//            out.writeShort(ProxyGenerator.this.cp.getUtf8(this.name));
//            out.writeShort(ProxyGenerator.this.cp.getUtf8(this.descriptor));
//            out.writeShort(2);
//            out.writeShort(ProxyGenerator.this.cp.getUtf8("Code"));
//            out.writeInt(12 + this.code.size() + 8 * this.exceptionTable.size());
//            out.writeShort(this.maxStack);
//            out.writeShort(this.maxLocals);
//            out.writeInt(this.code.size());
//            this.code.writeTo(out);
//            out.writeShort(this.exceptionTable.size());
//            Iterator var2 = this.exceptionTable.iterator();
//
//            while(var2.hasNext()) {
//                ProxyGenerator.ExceptionTableEntry e = (ProxyGenerator.ExceptionTableEntry)var2.next();
//                out.writeShort(e.startPc);
//                out.writeShort(e.endPc);
//                out.writeShort(e.handlerPc);
//                out.writeShort(e.catchType);
//            }
//
//            out.writeShort(0);
//            out.writeShort(ProxyGenerator.this.cp.getUtf8("Exceptions"));
//            out.writeInt(2 + 2 * this.declaredExceptions.length);
//            out.writeShort(this.declaredExceptions.length);
//            short[] var6 = this.declaredExceptions;
//            int var7 = var6.length;
//
//            for(int var4 = 0; var4 < var7; ++var4) {
//                short value = var6[var4];
//                out.writeShort(value);
//            }
//
//        }
//    }
//
//    private static class ExceptionTableEntry {
//        public short startPc;
//        public short endPc;
//        public short handlerPc;
//        public short catchType;
//
//        public ExceptionTableEntry(short startPc, short endPc, short handlerPc, short catchType) {
//            this.startPc = startPc;
//            this.endPc = endPc;
//            this.handlerPc = handlerPc;
//            this.catchType = catchType;
//        }
//    }
//
//    private class FieldInfo {
//        public int accessFlags;
//        public String name;
//        public String descriptor;
//
//        public FieldInfo(String name, String descriptor, int accessFlags) {
//            this.name = name;
//            this.descriptor = descriptor;
//            this.accessFlags = accessFlags;
//            ProxyGenerator.this.cp.getUtf8(name);
//            ProxyGenerator.this.cp.getUtf8(descriptor);
//        }
//
//        public void write(DataOutputStream out) throws IOException {
//            out.writeShort(this.accessFlags);
//            out.writeShort(ProxyGenerator.this.cp.getUtf8(this.name));
//            out.writeShort(ProxyGenerator.this.cp.getUtf8(this.descriptor));
//            out.writeShort(0);
//        }
//    }
//}
