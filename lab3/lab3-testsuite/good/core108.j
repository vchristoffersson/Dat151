.class public core108
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core108/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 4
istore_0
ldc 1
iload 0
ldc 6
if_icmplt L
pop
ldc 0
L:
ifeq END
pop
goto TRUE
FALSE:
TRUE:
iload 0
ireturn
return
.end method
