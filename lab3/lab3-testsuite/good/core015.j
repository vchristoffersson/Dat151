.class public core015
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core015/main()I
pop
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
.method public static ev(I)I
.limit locals 100
.limit stack 100
ldc 1
iload 0
ldc 0
if_icmpgt L
pop
ldc 0
L:
ifeq END
istore 1
iload 1
pop
goto TRUE
FALSE:
ldc 1
iload 0
ldc 0
if_icmplt L
pop
ldc 0
L:
ifeq END
ldc 0
istore 1
iload 1
pop
goto TRUE
FALSE:
ldc 1
istore 1
iload 1
pop
TRUE:
TRUE:
iload 1
ireturn
return
.end method
