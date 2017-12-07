.class public scopes
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic scopes/main()I
pop
return
.end method
.method public static f()I
.limit locals 100
.limit stack 100
ldc 2
istore_0
ldc 1
iload 0
ldc 3
if_icmplt L
pop
ldc 0
L:
ifeq END
ldc 3
istore_1
iload 1
ireturn
goto TRUE
FALSE:
TRUE:
iload 0
ireturn
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 1
istore_2
pop
pop
pop
ldc 0
ireturn
return
.end method
