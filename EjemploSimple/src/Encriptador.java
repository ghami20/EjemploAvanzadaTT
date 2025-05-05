
public interface Encriptador {
	
	default String encriptar(String texto) {
		String resultado = "";
		for (char c : texto.toCharArray()) {
			if (Character.isLetter(c)) {
				char base = Character.isLowerCase(c) ? 'a' : 'A';
				c = (char) ((c - base + 3) % 26 + base);
			}
			resultado += c;
		}
		return resultado;
	}

	default String desencriptar(String texto) {
		String resultado = "";
		for (char c : texto.toCharArray()) {
			if (Character.isLetter(c)) {
				char base = Character.isLowerCase(c) ? 'a' : 'A';
				c = (char) ((c - base - 3 + 26) % 26 + base);
			}
			resultado += c;
		}
		return resultado;
	}

}
