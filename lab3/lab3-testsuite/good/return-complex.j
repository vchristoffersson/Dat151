.class public return-complex
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic return-complex/main()I
pop
return
.end method
.method public static foo(I)Z
.limit locals 100
.limit stack 100
ldc 1
iload 0
ldc 0
if_icmpeq L
pop
ldc 0
L:
ifeq END
ldc 0
ireturn
goto TRUE
FALSE:
TRUE:
ldc 1
istore_1
pop
iload 1
ireturn
return
.end method
.method public static printBool(Z)V
.limit locals 100
.limit stack 100
ldc 0
ifeq END
goto TRUE
FALSE:
iload 2
ifeq END
pop
goto TRUE
FALSE:
pop
TRUE:
TRUE:
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
pop
pop
ldc 0
ireturn
pop
return
.end method
