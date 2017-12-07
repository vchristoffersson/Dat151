.class public core016
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core016/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 17
istore_0
WHILE:
ldc 1
iload 0
ldc 0
if_icmpgt L
pop
ldc 0
L:
ifeq END
iload 0
ldc 2
isub
istore 0
iload 0
pop
goto WHILE
END
ldc 1
iload 0
ldc 0
if_icmplt L
pop
ldc 0
L:
ifeq END
pop
ldc 0
ireturn
goto TRUE
FALSE:
pop
ldc 0
ireturn
TRUE:
return
.end method
