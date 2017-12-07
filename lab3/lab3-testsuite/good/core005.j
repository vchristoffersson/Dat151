.class public core005
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core005/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 56
istore_1
ldc 1
iload 1
ldc 45
iadd
ldc 2
if_icmple L
pop
ldc 0
L:
ifeq END
ldc 1
istore 0
iload 0
pop
goto TRUE
FALSE:
ldc 2
istore 0
iload 0
pop
TRUE:
pop
ldc 0
ireturn
return
.end method
