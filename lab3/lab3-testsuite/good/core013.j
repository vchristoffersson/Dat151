.class public core013
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core013/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
pop
pop
pop
pop
pop
pop
ldc 0
ireturn
return
.end method
.method public static printBool(Z)V
.limit locals 100
.limit stack 100
iload 0
ifeq END
pop
goto TRUE
FALSE:
pop
TRUE:
return
.end method
.method public static test(I)Z
.limit locals 100
.limit stack 100
ldc 1
iload 1
ldc 0
if_icmpgt L
pop
ldc 0
L:
ireturn
return
.end method
