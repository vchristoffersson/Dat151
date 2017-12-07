.class public core113
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core113/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
pop
pop
ldc 0
ireturn
return
.end method
.method public static f(I)I
.limit locals 100
.limit stack 100
ldc 1
iload 0
ldc 100
if_icmplt L
pop
ldc 0
L:
ifeq END
ldc 91
istore_2
iload 2
istore 1
iload 1
pop
goto TRUE
FALSE:
iload 0
istore 1
iload 1
pop
TRUE:
iload 1
ireturn
return
.end method
