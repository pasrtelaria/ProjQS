package org.example;

public class Login {
    public static void loginUsuario(Usuario usuario, String Senha) {
        {
            if (usuario.getSenha().equals(Senha)) {
                usuario.login();
                if (usuario instanceof Admin) {
                    ((Admin) usuario).gereDepartamento();
                } else if (usuario instanceof Coordenador) {
                    ((Coordenador) usuario).gereCurso();
                }
            } else {
                System.out.println("Senha Incorreta para este usuário");
            }
        }
    }
}