.class public return-in-if-right
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic return-in-if-right/main()I
pop
return
.end method
.method public static g()I
.limit locals 100
.limit stack 100
ldc 0
ifeq END
ldc 12
ireturn
goto TRUE
FALSE:
ldc 11
ireturn
TRUE:
ldc 10
ireturn
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
pop
ldc 0
ireturn
return
.end method
