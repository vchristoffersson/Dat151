.class public eval-order
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic eval-order/main()I
pop
return
.end method
.method public static order(II)I
.limit locals 100
.limit stack 100
ldc 0
ireturn
return
.end method
.method public static printIntInt(I)I
.limit locals 100
.limit stack 100
pop
iload 2
ireturn
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
pop
iadd
pop
ldc 1
if_icmpeq L
pop
ldc 0
L:
pop
ldc 0
ireturn
return
.end method
