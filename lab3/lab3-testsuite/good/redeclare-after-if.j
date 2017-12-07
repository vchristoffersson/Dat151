.class public redeclare-after-if
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic redeclare-after-if/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 1
ldc 0
ldc 2
if_icmple L
pop
ldc 0
L:
ifeq END
ldc 1
istore_0
goto TRUE
FALSE:
ldc 0
pop
TRUE:
ldc 0
istore_1
pop
ldc 0
ireturn
return
.end method
