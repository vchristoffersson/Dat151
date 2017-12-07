.class public cmp
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic cmp/main()I
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
