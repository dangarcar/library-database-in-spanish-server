package es.library.databaseserver;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.crud.service.ContenidoService;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;
import es.library.databaseserver.contenido.types.Video;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.prestamos.transacciones.service.PrestamosTransaccionesService;

@SpringBootTest
class DatabaseServerApplicationTests {

	private Logger logger = LogManager.getLogger(this.getClass());
	
	private static final Random RNG = new Random();
	
	@Autowired
	private ContenidoService contenidoService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private PrestamosTransaccionesService prestamosTransaccionesService;
	
	@Test
	@Disabled
	void populateTestDatabase() {
		final int nContenidos = 10;
		final int nPerfiles = 10;
		final int nPrestamos = 10;
		
		generateContenidos(nContenidos);
		generatePerfiles(nPerfiles);
		generatePrestamos(nPrestamos);
	}
	
	private void generateContenidos(int cantidad) {		
		for (int i=0;i<cantidad;i++) {
			int n = RNG.nextInt(0, 99);
			
			Contenido c = null;
			
			if (n < 50) {
				
				c = new Libro(null, getRandomString(5), getRandomString(3), getRandomString(20), RNG.nextInt(2020)+1, "Chino", Soporte.E_BOOK, true, RNG.nextInt(99)+1, true, null, ((Long)RNG.nextLong()).toString(), RNG.nextInt(999)+1, getRandomString(4));
			}
			else if(n >75){
				
				c = new Video(null, getRandomString(5), getRandomString(3), getRandomString(20), RNG.nextInt(2020)+1, "Chino", Soporte.BLURAY, true, RNG.nextInt(99)+1, true, null, RNG.nextDouble(1000.1)+1, RNG.nextInt(17)+1, RNG.nextInt(1060)+20);
			}
			else {
				
				c = new Audio(null, getRandomString(5), getRandomString(3), getRandomString(20), RNG.nextInt(2020)+1, "Chino", Soporte.BLURAY, true, RNG.nextInt(99)+1, true, null, RNG.nextDouble(1000.1)+1);
			}
			
			contenidoService.insertContenido(c);
			
			logger.info("Contenido número {} \tTítulo: {} \tTipo: {}", i, c.getTitulo(), c.getClass().getSimpleName());
		}
	}
	
	private void generatePerfiles(int cantidad) {
		for (int i=0;i<cantidad;i++) {
			
			Perfil perfil = null;
			
			perfil = new Perfil(null, getRandomString(2), LocalDate.of(RNG.nextInt(2020-12)+1, RNG.nextInt(11)+1, RNG.nextInt(27)+1), getRandomString(2).replaceAll(" ", "")
					.replaceAll("á", "a")
					.replaceAll("é", "e")
					.replaceAll("í", "i")
					.replaceAll("ó", "o")
					.replaceAll("ñ", "nn")
					.replaceAll("ú", "u").toLowerCase()+"@gmail.com", "abcd1234", Roles.values()[RNG.nextInt(Roles.values().length)]);
			
			perfilService.insertPerfil(perfil);
			
			logger.info("Perfil número {} \tNombre: {} \tRol: {}", i, perfil.getNombre(), perfil.getClass().getSimpleName());
		}
	}
	
	private void generatePrestamos(int cantidad) {			
		List<Long> contenidoList = contenidoService.getAllContenidos().stream().map(Contenido::getID).toList();
		
		List<Long> perfiList = perfilService.getAllPerfiles().stream().map(Perfil::getID).toList();
		
		for(var i=0;i<cantidad;i++) {			
			try {
				long contenidoId = contenidoList.get(RNG.nextInt(contenidoList.size()));
				
				long perfilId = perfiList.get(RNG.nextInt(perfiList.size()));
				
				prestamosTransaccionesService.prestar(contenidoId,perfilId).getID();
				
				logger.info("Préstamo número {} \tContenido: {} \tPerfil: {}", i, contenidoId, perfilId);
				
			} catch(Exception e) {
				logger.error("Préstamo error: "+e.getMessage());
			}
		}
	}
	
	private static String getRandomString(int maxWords) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(WORDS[RNG.nextInt(WORDS.length)]);
		
		for(int i=0;i<=RNG.nextInt(maxWords);i++) {
			builder.append(" "+WORDS[RNG.nextInt(WORDS.length)]);
		}
		
		return builder.toString();
	}
	
	private static final String[] WORDS = { "que", "de", "no", "a", "la", "el", "es", "y", "en", "lo", "un", "por", "qué",
			"me", "una", "te", "los", "se", "con", "para", "mi", "está", "si", "bien", "pero", "yo", "eso", "las", "sí",
			"su", "tu", "aquí", "del", "al", "como", "le", "más", "esto", "ya", "todo", "esta", "vamos", "muy", "hay",
			"ahora", "algo", "estoy", "tengo", "nos", "tú", "nada", "cuando", "ha", "este", "sé", "estás", "así",
			"puedo", "cómo", "quiero", "sólo", "soy", "tiene", "gracias", "o", "él", "bueno", "fue", "ser", "hacer",
			"son", "todos", "era", "eres", "vez", "tienes", "creo", "ella", "he", "ese", "voy", "puede", "sabes",
			"hola", "sus", "porque", "dios", "quién", "nunca", "dónde", "quieres", "casa", "favor", "esa", "dos", "tan",
			"señor", "tiempo", "verdad", "estaba", "mejor", "están", "va", "hombre", "usted", "mucho", "hace",
			"entonces", "siento", "tenemos", "puedes", "ahí", "ti", "vida", "ver", "alguien", "sr", "hasta", "sin",
			"mí", "solo", "años", "sobre", "decir", "uno", "siempre", "oh", "ir", "cosas", "también", "antes", "has",
			"ni", "mis", "día", "estar", "estamos", "noche", "nadie", "otra", "quiere", "parece", "nosotros", "poco",
			"padre", "trabajo", "gente", "mira", "vas", "sea", "les", "donde", "mismo", "hecho", "ellos", "dijo",
			"pasa", "dinero", "hijo", "tal", "otro", "hablar", "seguro", "claro", "estas", "lugar", "mundo", "amigo",
			"espera", "mierda", "han", "tus", "sabe", "después", "momento", "desde", "fuera", "cosa", "tipo", "mañana",
			"podemos", "dije", "gran", "necesito", "estado", "podría", "acuerdo", "papá", "tener", "dice", "mío",
			"crees", "buena", "gusta", "nuestro", "nuevo", "será", "haciendo", "días", "nombre", "buen", "había", "ven",
			"tres", "menos", "debe", "tenía", "mal", "conmigo", "madre", "hoy", "quien", "sido", "mamá", "tienen",
			"luego", "todas", "allí", "toda", "hora", "mujer", "visto", "haces", "importa", "contigo", "ve", "tarde",
			"oye", "parte", "haber", "hombres", "problema", "mas", "saber", "quería", "aún", "veces", "nuestra",
			"hacerlo", "cada", "hizo", "veo", "tanto", "razón", "ustedes", "idea", "esos", "van", "quizá", "debo",
			"alguna", "cierto", "ud", "muerto", "unos", "estos", "salir", "policía", "realmente", "demasiado",
			"familia", "pueden", "cabeza", "hemos", "amigos", "chica", "cariño", "lado", "allá", "entre", "minutos",
			"digo", "algún", "serio", "cuidado", "pasó", "buenas", "somos", "amor", "puerta", "ves", "vaya", "ah",
			"suerte", "eh", "rápido", "cuenta", "quizás", "io", "esas", "pues", "pasado", "pensé", "todavía", "hermano",
			"debes", "casi", "forma", "aqui", "chico", "ok", "dicho", "nueva", "sabía", "muchas", "dentro", "hice",
			"contra", "auto", "camino", "ayuda", "primera", "hacia", "vi", "miedo", "adiós", "primero", "debería",
			"poder", "niños", "sería", "historia", "hey", "mientras", "ciudad", "dijiste", "espero", "cuánto", "esposa",
			"pronto", "chicos", "cualquier", "viejo", "debemos", "deja", "año", "muerte", "hablando", "manos", "da",
			"loco", "problemas", "mano", "guerra", "semana", "pasar", "vale", "cuál", "viene", "volver", "toma", "caso",
			"agua", "haré", "vete", "entiendo", "horas", "personas", "capitán", "adelante", "niño", "listo", "noches",
			"buenos", "iba", "juntos", "dame", "único", "déjame", "cerca", "otros", "sigue", "grande", "arriba", "jefe",
			"habla", "supongo", "manera", "quieren", "feliz", "significa", "sangre", "fin", "bajo", "llama", "venir",
			"morir", "importante", "hiciste", "ojos", "escucha", "entrar", "ningún", "corazón", "diablos",
			"necesitamos", "atrás", "durante", "dices", "nuestros", "persona", "abajo", "dr", "hija", "dejar",
			"necesita", "llegar", "hago", "señora", "haya", "suficiente", "doctor", "gustaría", "tierra", "cara",
			"siquiera", "genial", "cree", "supuesto", "tomar", "equipo", "justo", "juego", "ninguna", "matar", "cinco",
			"dicen", "amo", "cuándo", "pequeño", "algunos", "conozco", "clase", "maldito", "unas", "muchos", "hubiera",
			"segundo", "aunque", "pueda", "dime", "igual", "comida", "ay", "cuerpo", "encontrar", "fuerte", "vuelta",
			"venga", "creer", "realidad", "saben", "puta", "deberías", "pregunta", "fui", "cuatro", "sra", "primer",
			"trabajar", "e", "hagas", "alto", "maldita", "comer", "número", "dar", "necesitas", "john", "oportunidad",
			"punto", "misma", "última", "afuera", "mujeres", "pensar", "fueron", "difícil", "vivir", "paso", "malo",
			"estabas", "vivo", "haga", "queda", "hijos", "mayor", "fiesta", "hacen", "medio", "algunas", "basta", "ei",
			"arma", "vino", "meses", "cuarto", "éste", "escuela", "esté", "dólares", "tío", "posible", "tuve", "fácil",
			"preocupes", "jack", "luz", "eran", "carajo", "final", "lista", "trata", "armas", "hermana", "exactamente",
			"chicas", "podía", "bastante", "seguridad", "pasando", "esperando", "acá", "teléfono", "perro", "fuego",
			"murió", "tampoco", "sola", "estuvo", "verte", "iré", "tenido", "culpa", "veras", "adónde", "buscando",
			"cuanto", "padres", "paz", "demonios", "estará", "cual", "perdón", "asi", "jugar", "pensando", "esperar",
			"sabemos", "recuerdo", "par", "joven", "seguir", "pueblo", "tenga", "caballeros", "idiota", "dio", "minuto",
			"bebé", "única", "lejos", "nuestras", "plan", "pienso", "sentido", "dormir", "digas", "palabra", "correcto",
			"control", "vemos", "entiendes", "país", "seis", "último", "ésta", "diga", "podrías", "pequeña", "cállate",
			"trato", "rey", "sucede", "sam", "muchachos", "jamás", "cama", "srta", "ayudar", "acerca", "di", "cambio",
			"falta", "hospital", "lleva", "presidente", "mil", "gusto", "conoces", "diciendo", "os", "ido", "general",
			"extraño", "semanas", "coche", "peor", "mucha", "disculpe", "diré", "anoche", "perder", "vámonos", "nave",
			"cielo", "habrá", "orden", "segura", "querida", "niña", "michael", "increíble", "además", "deben", "libro",
			"calle", "café", "piensas", "hacemos", "especial", "queremos", "ia", "clark", "irme", "perfecto", "buscar",
			"odio", "piensa", "oficina", "hablas", "libre", "agente", "york", "llamar", "mala", "detrás", "viste",
			"dile", "grandes", "recuerdas", "real", "estaban", "mía", "frente", "perdido", "llamo", "muertos",
			"millones", "asesino", "sueño", "quisiera", "habría", "hará", "viaje", "probablemente", "peter", "resto",
			"estaré", "maldición", "lamento", "muchacho", "avión", "ropa", "fuerza", "llamado", "oído", "frank", "dado",
			"encima", "negro", "usar", "información", "uds", "preguntas", "tuvo", "secreto", "vuelve", "miren",
			"quieras", "haría", "acaba", "otras", "incluso", "sientes", "deberíamos", "haz", "decirte", "boca", "dolor",
			"baño", "adentro", "profesor", "habitación", "daño", "tuyo", "seas", "noticias", "demás", "querido", "duro",
			"poner", "prueba", "mire", "tonto", "campo", "siendo", "diez", "ése", "tranquilo", "asunto", "acabó",
			"quédate", "derecho", "placer", "recuerda", "estuve", "tratando", "ejército", "futuro", "llevar",
			"compañía", "venido", "listos", "haremos", "sitio", "verlo", "puesto", "atención", "sino", "cambiar",
			"error", "blanco", "raro", "palabras", "llegó", "sal", "pase", "mente", "sistema", "película", "anda",
			"ello", "negocio", "novia", "permiso", "creí", "suena", "ocurre", "oficial", "espere", "aire", "george",
			"mató", "harry", "regresar", "vio", "hazlo", "trasero", "grupo", "entendido", "señorita", "música", "perra",
			"conoce", "empezar", "siente", "acabo", "estúpido", "diferente", "traje", "modo", "james", "encontré",
			"mensaje", "llamada", "navidad", "eras", "pena", "largo", "entra", "piso", "foto", "dijeron", "médico",
			"accidente", "fuiste", "imposible", "podríamos", "línea", "propia", "barco", "ganar", "normal", "segundos",
			"vive", "mitad", "quiera", "tras", "decirle", "lindo", "funciona", "programa", "vine", "abre", "sean",
			"pagar", "fotos", "centro", "supone", "basura", "situación", "mejores", "vienen", "encanta", "marido",
			"personal", "maestro", "hambre", "ataque", "culo", "dale", "pie", "conseguir", "trabajando", "gracioso",
			"dejó", "pudo", "derecha", "izquierda", "próxima", "pobre", "respuesta", "tipos", "sentir", "tenías",
			"pude", "darle", "voz", "amiga", "gustan", "vista", "salvo", "loca", "hotel", "hicieron", "ten", "temo",
			"señal", "pelo", "llevo", "ayer", "das", "nena", "servicio", "tren", "tom", "bonito", "mes", "tendrá",
			"tendrás", "edad", "ellas", "hermosa", "ben", "honor", "simplemente", "llamas", "tengas", "corre", "baja",
			"sol", "siéntate", "dan", "humano", "divertido", "sexo", "vuelto", "peligro", "mesa", "jimmy", "siguiente",
			"hablo", "disculpa", "decirme", "joe", "caja", "negocios", "misión", "silencio", "sale", "llegado",
			"estaría", "regreso", "media", "estan", "propio", "charlie", "oro", "enseguida", "linda", "prometo",
			"esposo", "norte", "hubo", "juro", "muerta", "interesante", "pensaba", "busca", "terminar", "tendré",
			"completamente", "cita", "siete", "cumpleaños", "abogado", "alrededor", "cerebro", "porqué", "llave",
			"santo", "hermoso", "necesario", "edificio", "irnos", "aun", "tendremos", "vayas", "doy", "trae", "salió",
			"ley", "ahi", "verdadero", "pelea", "banco", "terrible", "calma", "cena", "daré", "gobierno", "comprar",
			"creen", "sargento", "destino", "bob", "existe", "hacía", "novio", "sala", "través", "regalo", "iglesia",
			"decía", "cualquiera", "excelente", "esperen", "deseo", "alma", "diablo", "deje", "cuántos", "espada",
			"estábamos", "carne", "maravilloso", "vidas", "sucedió", "oí", "peligroso", "dirección", "libertad",
			"jesús", "ocurrió", "veré", "sueños", "pudiera", "detective", "sorpresa", "tuya", "pies", "club",
			"terminado", "infierno", "creía", "luna", "salvar", "carta", "estés", "cielos", "teniente", "encuentra",
			"david", "veamos", "quise", "escúchame", "necesitan", "ambos", "decisión", "roma", "enemigo", "hicimos",
			"éi", "dulce", "pruebas", "querías", "abuelo", "totalmente", "mirando", "vayan", "carrera", "vuelo", "ante",
			"bienvenido", "harás", "encontramos", "encontrado", "contacto", "posición", "saberlo", "planeta", "humanos",
			"coronel", "junto", "diría", "ésa", "base", "oír", "suelo", "pelear", "ayudarte", "pistola", "frío",
			"comandante", "partes", "llega", "verás", "sur", "iremos", "rato", "mar", "espacio", "asesinato", "ventana",
			"prisa", "tienda", "cámara", "puedas", "según", "broma", "reunión", "despierta", "sacar", "tí", "segunda",
			"papel", "locura", "departamento", "horrible", "enfermo", "pregunto", "cárcel", "órdenes", "intento",
			"isla", "salida", "llamó", "volveré", "usa", "gato", "paul", "hagan", "dejes", "duele", "vengan", "crimen",
			"esperaba", "causa", "bar", "seré", "ocho", "temprano", "río", "relación", "drogas", "luces", "bromeando",
			"ojalá", "hablamos", "trabaja", "irse", "libros", "radio", "mary", "ray", "bill", "vienes", "quedan",
			"excepto", "brazo", "tome", "rojo", "conocido", "universidad", "investigación", "batalla", "reglas",
			"cargo", "hogar", "ninguno", "dieron", "vuelva", "sabías", "respeto", "estación", "corte", "paciente",
			"encuentro", "energía", "dejado", "baile", "fbi", "abuela", "caliente", "vieja", "viendo", "veremos",
			"rayos", "simple", "bailar", "papa", "triste", "zona", "serás", "guardia", "canción", "salud", "escuchar",
			"parar", "mike", "estarás", "cenar", "max", "soldados", "caballo", "serán", "estaremos", "interesa",
			"volar", "principio", "nivel", "cálmate", "conocer", "finalmente", "alegro", "debajo", "podrían", "bosque",
			"bonita", "bolsa", "pone", "taxi", "ocupado", "amable", "ryan", "acaso", "detente", "imbécil", "san",
			"equivocado", "viva", "puso", "obra", "consejo", "público", "ayúdame", "animales", "azul", "apuesto",
			"prisión", "mirar", "inteligente", "metros", "fantástico", "próximo", "jugando", "ojo", "salga", "vea",
			"llaman", "entrada", "duda", "cerveza", "unidos", "matado", "princesa", "perdí", "entender", "santa",
			"quedar", "miles", "llamaré", "compañero", "pensado", "espalda", "dejé", "bomba", "alex", "cartas",
			"apenas", "leer", "hermanos", "darme", "papi", "mantener", "suyo", "rico", "verla", "lee", "bobby",
			"sigues", "toca", "olvídalo", "acción", "hayas", "dioses", "mando", "dejaré", "llegue", "formas", "uh",
			"henry", "cierra", "damas", "puente", "memoria", "regresa", "muévanse", "parecía", "vestido", "llaves",
			"tv", "camión", "acabar", "robot", "llevó", "montón", "estuviste", "máquina", "puertas", "podamos", "muere",
			"trago", "mayoría", "reina", "lleno", "inglés", "don", "soldado", "estrella", "escuche", "valor", "pido",
			"delante", "código", "héroe", "fe", "capaz", "verme", "beber", "velocidad", "darte", "llevas", "partido",
			"estuviera", "tony", "lex", "dia", "opinión", "irte", "cocina", "abrir", "perros", "tambien", "sepa",
			"pareces", "escribir", "golpe", "tenia", "alta", "estados", "tocar", "vuelvo", "habían", "ganas", "hacerte",
			"dejo", "volvió", "ejemplo", "robert", "contar", "tenían", "propósito", "entiende", "empieza", "anillo",
			"londres", "tendría", "pedir", "estilo", "ayudarme", "pista", "cambiado", "escapar", "eeuu", "encontró",
			"espíritu", "viejos", "molesta", "nota", "diferencia", "tratar", "precio", "caballero", "sirve", "alegra",
			"doble", "agradable", "embargo", "tengan", "tuvimos", "sube", "estén", "mami", "emergencia", "cuello",
			"boda", "aprender", "pete", "té", "informe", "blanca", "experiencia", "debía", "podido", "mentira",
			"director", "mata", "exacto", "eddie", "déjalo", "defensa", "tuviste", "confiar", "color", "bond",
			"hacerle", "aquel", "conocí", "probar", "príncipe", "irá", "sóio", "ai", "despacio", "tiro", "vacaciones",
			"perdió", "fondo", "chris", "verde", "parecen", "mama", "bienvenida", "opción", "operación", "jim",
			"hablado", "traer", "principal", "demonio", "zapatos", "leo", "anna", "respecto", "nuevos", "especie",
			"pidió", "éxito", "cabrón", "mírame", "piernas", "unidad", "hielo", "llegamos", "déjeme", "ios", "debí",
			"vendrá", "matrimonio", "habías", "podrá", "piel", "preocupa", "quedarme", "mio", "tuviera", "tiempos",
			"richard", "quienes", "oiga", "últimos", "oigan", "fuimos", "arte", "necesitaba", "quiénes", "común",
			"intentando", "llevaré", "levántate", "correr", "brillante", "calor", "planes", "dra", "visita", "presión",
			"pared", "esperanza", "completo", "subir", "cansado", "llame", "recordar", "trampa", "monstruo", "bajar",
			"pierna", "señores", "tomó", "pasará", "mataste", "grandioso", "socio", "herido", "cayó", "fuerzas", "nick",
			"cine", "escena", "tía", "danny", "dando", "puntos", "teníamos", "dejaste", "muévete", "larga", "solía",
			"tema", "preparado", "depende", "policías", "solos", "matarme", "mark", "pon", "hubiese", "bebe", "tanta",
			"oeste", "parís", "viento", "jóvenes", "hacerme", "asiento", "carter", "washington", "estupendo", "caminar",
			"juicio", "creerlo", "carga", "tranquila", "banda", "estarán", "animal", "escuchen", "bush", "encontraron",
			"dejas", "tarjeta", "ibas", "podrás", "nueve", "seguramente", "volverá", "majestad", "llena", "caer",
			"aeropuerto", "vayamos", "tommy", "acabado", "sigo", "enorme", "área", "muevas", "haberlo", "dejen",
			"ridículo", "reloj", "hagamos", "flores", "mapa", "culpable", "cuentas", "pareja", "tomando", "m",
			"laboratorio", "beso", "empezó", "pedí", "nariz", "cuestión", "terminó", "vengo", "the", "perdona",
			"acuerdas", "verano", "prefiero", "peso", "billy", "oscuridad", "diferentes", "cliente", "charles",
			"cuántas", "televisión", "suya", "prensa", "johnny", "vistazo", "inmediatamente", "solamente", "gordo",
			"quedarse", "posibilidad", "medicina", "malditos", "pantalones", "mataron", "ayude", "podremos", "locos",
			"nombres", "superman", "parecer", "américa", "definitivamente", "cinta", "desea", "brazos", "asuntos",
			"riesgo", "escuché", "lugares", "i", "cuento", "noticia", "dígame", "decirlo", "tonterías", "sentimientos",
			"objetivo", "pasada", "arreglar", "casado", "bote", "roja", "leyes", "millón", "árbol", "hable", "proyecto",
			"nervioso", "lana", "salido", "pedazo", "malas", "joder", "humana", "absolutamente", "evitar", "corriendo",
			"bala", "vosotros", "despues", "debido", "confianza", "especialmente", "intenta", "pequeños", "paga",
			"ganado", "ama", "limpio", "comenzar", "ángeles", "robots", "lleve", "ruido", "preocupe", "jason",
			"belleza", "salgan", "come", "verá", "deberían", "líder", "preguntar", "inocente", "andy", "lástima", "red",
			"enfermedad", "desgraciado", "harán", "dientes", "maté", "central", "ponte", "felices", "supe", "haberte",
			"marcha", "poderes", "hubieras", "justicia", "bella", "dé", "proteger", "cabello", "pedido", "oscuro",
			"sarah", "embarazada", "costa", "dama", "alguno", "orgulloso", "vos", "tomado", "digamos", "debió", "gana",
			"cura", "lengua", "araña", "podré", "voluntad", "total", "dedo", "pago", "agradezco", "partir", "lucha",
			"leche", "luke", "diste", "copa", "quedarte", "seguros", "destruir", "bienvenidos", "llevan", "jerry",
			"ponga", "lados", "acceso", "parker", "intentar", "trajo", "desastre", "deber", "herida", "débil",
			"naturaleza", "luchar", "extraña", "encontraste", "vieron", "llámame", "imagen", "pan", "refieres",
			"clientes", "varios", "show", "auxilio", "oyes", "siguen", "sentado", "saliendo", "responsable", "olvidado",
			"quedó", "permite", "aquella", "puse", "sonido", "quiso", "escrito", "carro", "preciosa", "muestra",
			"bastardo", "restaurante", "huevos", "fantasma", "mismos", "profesional", "amas", "entero", "películas",
			"cerrado", "dedos", "sigan", "dueño", "cuchillo", "turno", "tormenta", "caray", "abierto", "quede",
			"verdadera", "mato", "doc", "playa", "km", "victoria", "primo", "oíste", "lisa", "interior", "hechos",
			"piedra", "jodido", "militar", "computadora", "encantaría", "siga", "silla", "movimiento", "momentos",
			"familiar", "apartamento", "disparar", "tesoro", "perfecta", "refiero", "motivo", "dará", "nuevas", "mr",
			"querer", "matarlo", "valiente", "víctima", "tendrán", "estúpida", "envió", "steve", "encantado", "diario",
			"abran", "sentí", "autobús", "traído", "casas", "apúrate", "encuentras", "descanso", "sheriff", "mulder",
			"mueve", "inglaterra", "quieto", "carl", "matarte", "calles", "tamaño", "colegio", "usando", "cabo",
			"video", "toques", "pelota", "yendo", "directo", "termine", "vivos", "sombrero", "tomé", "estudio", "grave",
			"protección", "secretos", "perdiendo", "pecho", "sepas", "enferma", "cuesta", "negros", "estrellas", "lago",
			"clases", "b", "obviamente", "inmediato", "modos", "irás", "robo", "mataré", "seria", "laura", "apoyo",
			"cerrar", "imagino", "amenaza", "toque", "larry", "saca", "viernes", "hayan", "roto", "lucy", "piloto",
			"marca", "combate", "hablé", "manejar", "sección", "profundo", "tantos", "conocía", "serie", "ed",
			"contrato", "americano", "paseo", "aviones", "balas", "repente", "cambia", "tropas", "llevará", "uso",
			"pudiste", "detener", "susan", "sabia", "llegué", "negra", "decidido", "comienza", "distancia", "éramos",
			"murieron", "averiguar", "actuar", "quedo", "pulso", "aguanta", "oficiales", "llamaba", "enemigos",
			"saldrá", "preocupado", "elección", "sobrevivir", "bordo", "sujeto", "pacientes", "disculpen", "época",
			"entró", "pesar", "habló", "walter", "vender", "piensan", "ex", "gratis", "desapareció", "empleo", "llamé",
			"conocen", "cerdo", "puedan", "comprendo", "tomo", "termina", "aléjate", "busco", "contento", "date",
			"tumba", "matt", "huellas", "cuerpos", "absoluto", "pide", "kent", "dura", "descansar", "respuestas",
			"propiedad", "llamando", "dormido", "castillo", "china", "datos", "torre", "robar", "americanos", "malos",
			"botella", "aquellos", "últimamente", "testigo", "s", "presento", "periódico", "kate", "juez", "historias",
			"gustaba", "créeme", "necesitar", "esquina", "millas", "llamadas", "fuente", "conocemos", "techo", "genio",
			"alerta", "ideas", "tomas", "olvidar", "menor", "droga", "explicar", "abierta", "privado", "miembros",
			"joey", "nacional", "gustó", "ross", "discutir", "evidencia", "detalles", "c", "tardes", "tomaré",
			"presente", "scott", "criminal", "premio", "vuelvas", "siéntese", "viniste", "recibir", "mires", "teniendo",
			"huele", "hables", "guardias", "enamorado", "cuidar", "pocos", "perdone", "natural", "caballos", "escucho",
			"enfermera", "ángel", "sospechoso", "local", "mentiras", "camisa", "confía", "aceptar", "venganza",
			"perdimos", "cuantos", "parque", "juegos", "chloe", "vives", "varias", "harías", "menudo", "déjenme",
			"barrio", "virus", "talento", "ministro", "hiciera", "robó", "conocerte", "viviendo", "responsabilidad",
			"respira", "números", "pertenece", "desayuno", "dave", "campeón", "controlar", "maría", "nieve", "méxico",
			"howard", "dias", "vergüenza", "doctora", "blancos", "fumar", "llevamos", "europa", "dudo", "athena",
			"pastel", "quedas", "cae", "vimos", "recibido", "andando", "agentes", "fueras", "asustado", "perdóname",
			"reino", "clave", "papeles", "matando", "jake", "efecto", "r", "plata", "olvidé", "iban", "universo",
			"política", "almuerzo", "magia", "lluvia", "humor", "autos", "veía", "tantas", "respirar", "cantar",
			"trabajas", "julie", "disparo", "conversación", "olor", "asesinos", "muchacha", "modelo", "jane", "curso",
			"canal", "rock", "llorar", "borracho", "regresen", "váyanse", "sentía", "resultado", "william", "parado",
			"olvides", "mirada", "inútil", "california", "acabas", "ciencia", "perdiste", "sociedad", "regrese",
			"camioneta", "coño", "revista", "pareció", "montaña", "maravillosa", "dejarlo", "máximo", "echar",
			"corriente", "escuchando", "disculpas", "cadáver", "seguimos", "ron", "espectáculo", "frodo", "mundial",
			"francia", "decirles", "aspecto", "presencia", "famoso", "corto", "relájate", "poderoso", "recuerdos",
			"llego", "carretera", "pasé", "oigo", "esperamos", "escuchado", "cable", "rosa", "bola", "oferta", "helen",
			"intentarlo", "habido", "continuar", "rachel", "ladrón", "humanidad", "estómago", "entera", "contó",
			"poniendo", "mercado", "granja", "idiotas", "hablemos", "estudiante", "siglo", "roger", "relaciones", "ias",
			"hablan", "adam", "nosotras", "miembro", "fortuna", "libres", "acciones", "esperas", "suficientemente",
			"llevaron", "ocurrido", "andar", "perfectamente", "pasan", "jardín", "cola", "resultados", "francés",
			"troya", "tercera", "martin", "entrenamiento", "doug", "azúcar", "metido", "ayudarlo", "diles", "coger",
			"lunes", "ey", "new", "intención", "volviendo", "efectivo", "chicago", "dei", "salgamos", "preguntaba",
			"olvida", "helicóptero", "fuertes", "encantador", "original", "cadena", "tarea", "dispara", "corta",
			"compañeros", "cirugía", "quítate", "querían", "precioso", "lord", "sara", "comenzó", "truco", "inspector",
			"enojado", "felicidades", "desaparecido", "acto", "pollo", "elizabeth", "chocolate", "adonde", "resulta",
			"merece", "gas", "examen", "vean", "tonta", "importantes", "apuesta", "jean", "directamente", "recuperar",
			"pongo", "llegaron", "cobarde", "cantidad", "sucio", "habia", "emperador", "mono", "encontraremos",
			"disparen", "gloria", "contrario", "socorro", "sir", "escaleras", "seth", "pasos", "nuevamente", "derechos",
			"obtener", "guste", "búsqueda", "helado", "fútbol", "sexual", "polvo", "aseguro", "rostro", "robado",
			"policia", "empresa", "escuchas", "pones", "lárgate", "interés", "discúlpeme", "dejaron", "tengamos",
			"firma", "llevaba", "llegas", "cristo", "tercer", "senador", "continúa", "gracia", "cien", "texas", "jones",
			"des", "tratado", "razones", "caminando", "urgencias", "u", "raymond", "esperan", "muñeca", "firme",
			"duerme", "ambulancia", "limpia", "habéis", "flota", "leyenda", "encuentre", "fué", "arturo", "romper",
			"muriendo", "encontrarlo", "rescate", "quedado", "enfrente", "comienzo", "árboles", "x", "queso",
			"favorito", "haberme", "frontera", "creemos", "colega", "jueves", "alemanes", "shrek", "sábado", "limpiar",
			"puto", "poquito", "cientos", "muero", "irak", "inteligencia", "funcionar", "felicitaciones", "escapó",
			"criatura", "milagro", "deprisa", "especiales", "campamento", "responde", "escuchaste", "debiste", "adios",
			"viven", "recoger", "preguntarte", "compras", "cansada", "will", "elegir", "cáncer", "servir", "pata",
			"huir", "útil", "grados", "atacar", "sonny", "recién", "querría", "guapo", "alcance", "smith", "dejame",
			"quedamos", "llegará", "seremos", "desierto", "t", "licencia", "fiona", "trató", "proceso", "llevado",
			"durmiendo", "tensión", "martes", "kilómetros", "cartera", "tráfico", "guarda", "impresionante", "víctimas",
			"ricos", "primeros", "dejamos", "tomen", "iguales", "domingo", "crear", "quedará", "pérdida", "extra",
			"fuese", "confío", "sabido", "análisis", "llegaste", "fría", "violencia", "mate", "lobo", "discurso",
			"actor", "sexy", "orgullo", "odia", "libras", "alli", "agencia", "vuelvan", "rose", "necesidad", "naves",
			"descubrir", "tira", "resolver", "pondré", "volando", "pensamos", "míos", "errores", "celular", "vincent",
			"cuantas", "claire", "verlos", "ayudarle", "meter", "hablaré", "ésto", "daniel", "buscas", "equivocada",
			"dejan", "vacío", "trabajaba", "salón", "motor", "conducir", "compré", "quizas", "den", "cerrada", "cortar",
			"ted", "crea", "sera", "mueva", "hazme", "bravo", "alan", "pregunté", "promesa", "espejo", "dígale",
			"sienta", "médicos", "cero", "escribió", "volveremos", "unidades", "cheque", "consigue", "antiguo", "regla",
			"pesadilla", "enviar", "podías", "llevarte", "entrenador", "cuida", "paciencia", "dilo", "levanta",
			"complicado", "superior", "sugiero", "familias", "dirá", "pienses", "temporada", "suelta", "jackie",
			"corran", "propios", "feo", "dispuesto", "ayúdenme", "teoría", "salvaje", "negativo", "íbamos", "hubieran",
			"tripulación", "taza", "perdida", "vinieron", "ponen", "pasamos", "parecido", "brian", "acepto", "gay",
			"cooper", "señoras", "quedaré", "parada", "niñas", "conseguí", "acabamos", "normalmente", "muchísimo",
			"bruja", "vegas", "tuvieron", "traigo", "matará", "kevin", "funeral", "conseguiste", "teatro", "paris",
			"medianoche", "biblioteca", "estemos", "túnel", "encargo", "aparece", "testigos", "pensó", "fueran",
			"fantasmas", "correo", "vuestro", "piscina", "paquete", "copia", "alice", "suicidio", "molly", "crédito",
			"consigo", "carol", "vuestra", "rara", "legal", "hablaremos", "cambió", "solas", "salimos", "ganador",
			"estudiantes", "despierto", "pare", "invitado", "guía", "oso", "leí", "explosión", "dejarme", "chino",
			"stan", "lois", "sorprende", "sistemas", "nación", "entienden", "arena", "mentiroso", "hablaba", "veas",
			"pura", "asesinado", "mintiendo", "casos", "vehículo", "rata", "importancia", "distinto", "pintura",
			"intenté", "conciencia", "miras", "máquinas", "traeré", "revisar", "veinte", "seres", "hablarle", "disco",
			"viktor", "transporte", "mina", "labios", "señoría", "llamamos", "doce", "cuídate", "pasillo", "tele",
			"podria", "completa", "rápida", "precisamente", "llamaste", "cumplir", "servicios", "señales", "acaban",
			"am", "suéltame", "hablarte", "traté", "pescado", "huesos", "daría", "compra", "trajiste", "templo",
			"placa", "posibilidades", "ira", "vengas", "siguiendo", "podrán", "llamarme", "cierta", "permitir",
			"condiciones", "atrapar", "temperatura", "explicación", "enviado", "contado", "asistente", "imperio",
			"pidiendo", "notas", "curioso", "operaciones", "mantén", "tierras", "creas", "pez", "morirá", "visión",
			"montañas", "jeff", "d", "comido", "construir", "comunidad", "verán", "pierde", "piense", "felicidad", "ee",
			"diversión", "salvó", "thomas", "quita", "museo", "disparó", "desearía", "misterio", "buscamos", "aviso",
			"almirante", "solución", "pequeñas", "faltan", "existen", "combustible", "leído", "tirar", "responder",
			"esfuerzo", "vuelves", "uu", "pájaro", "hermanas", "guerrero", "esposas", "coma", "abra", "traes",
			"sientas", "rocky", "olvide", "joseph", "bolsillo", "heridas", "saco", "gasolina", "conexión", "correcta",
			"pedazos", "líneas", "estarías", "convierte", "ayudará", "rumbo", "rompió", "muera", "mia", "material",
			"diamantes", "rápidamente", "mírate", "fecha", "escritorio", "digan", "registro", "maten", "incendio",
			"deme", "sois", "pensamientos", "dímelo", "comiendo", "aparte", "asesinatos", "roy", "lección", "algun",
			"abrigo", "tratamiento", "julio", "pene", "conejo", "suceder", "ruta", "ciego", "asqueroso", "súper",
			"prisionero", "odie", "ocupada", "llevarlo", "jenny", "empezamos", "emily", "ethan", "estuvimos",
			"decírselo", "oscar", "honesto", "entrevista", "antigua", "amante", "humo", "gustas", "condado",
			"conseguido", "viajar", "sombra", "sabrá", "refiere", "lío", "estáis", "entrega", "drácula", "compartir",
			"angeles", "volvamos", "sonrisa", "sencillo", "atrapado", "aburrido", "pocas", "pensaste", "dragón", "anne",
			"volví", "planta", "clima", "adoro", "suave", "ruso", "rodillas", "marina", "bolso", "betty", "alarma",
			"vivía", "quédese", "recompensa", "juega", "hollywood", "cigarrillo", "autoridad", "amigas", "agujero",
			"acabe", "sacaste", "orgullosa", "jackson", "entendí", "dijera", "sótano", "paredes", "guapa", "iría",
			"imaginar", "fiebre", "déjala", "botón", "preguntarle", "leyendo", "darles", "uniforme", "dulces", "vaso",
			"documentos", "ruego", "maneras", "king", "invitados", "cómodo", "ayudarnos", "visitar", "vigilancia",
			"nadar", "altura", "todavia", "preparados", "gary", "cuáles", "mover", "jugador", "izquierdo", "alejandro",
			"tecnología", "raza", "conocerlo", "conde", "social", "gobernador", "coge", "pedro", "cubierta", "chiste",
			"celda", "agarra", "secreta", "personalmente", "miller", "amistad", "amaba", "nació", "interesado",
			"intentado", "educación", "curiosidad", "buscan", "repito", "pecado", "mortal", "manda", "claramente",
			"vía", "juntas", "identificación", "dirige", "declaración", "alemán", "tontos", "mantiene", "bestia",
			"veneno", "vecinos", "supiera", "quieran", "salgo", "propias", "privada", "jurado", "golpes", "usan",
			"metas", "lenny", "garganta", "invierno", "fila", "ricky", "miel", "traigan", "sucedido", "tranquilos",
			"madera", "burro", "artista", "sophie", "pobres", "louis", "impresión", "ganó", "cruzar", "cigarrillos",
			"scully", "ó", "llevarme", "grial", "chaqueta", "alcalde", "vueltas", "usado", "reputación", "pongas",
			"oxígeno", "cajas", "preocupada", "matan", "liz", "empezando", "cabezas", "ponerme", "experto", "océano",
			"maestra", "cia", "pieza", "cargos", "bombas", "alcohol", "trataba", "salen", "pizza", "patrick", "recibí",
			"junta", "criaturas", "apareció", "actitud", "gira", "cristal", "bourne", "amy", "encuentran", "dejarte",
			"caído", "ultima", "j", "sensación", "seguido", "puro", "whisky", "vernos", "pantalla", "bebida",
			"estudiar", "ducha", "despedido", "consiguió", "venía", "pagan", "discúlpame", "artículo", "aparentemente",
			"ambas", "trauma", "pusieron", "fiestas", "contesta", "contenta", "ambiente", "prometí", "llegando",
			"mostraré", "deba", "berlín", "nerviosa", "hallar", "dudas", "llegan", "instrucciones", "baje", "vinimos",
			"ritmo", "quédense", "suponía", "marcus", "pistas", "palacio", "gordon", "encontraré", "venta", "urgente",
			"tomamos", "salí", "cuerda", "vendría", "pasaba", "instante", "almas", "suficientes", "secundaria",
			"saltar", "queria", "quedarnos", "molesto", "colina", "porquería", "dean", "sienten", "pondrá",
			"terroristas", "empecé", "pongan", "phoebe", "moto", "opinas", "habilidad", "entren", "descubierto",
			"convertido", "asegúrate", "summer", "quedé", "kilos", "florida", "tomará", "sufrir", "mental", "lograr",
			"instituto", "um", "serlo", "rapido", "plato", "llorando", "vd", "váyase", "sucediendo", "robaron",
			"alemania", "bolas", "luthor", "pasaría", "ocurra", "ilegal", "fresco", "falso", "abogados", "tómalo",
			"mandó", "cuán", "cuales", "botas", "tiburones", "marissa", "grado", "deseas", "archivos", "adivina",
			"cruz", "campaña", "asustada", "centavos", "vivimos", "galletas", "frecuencia", "daños", "minas",
			"emociones", "dejará", "regresó", "ratas", "pasión", "julia", "joel", "hacerse", "comportamiento", "pense",
			"conocimiento", "bin", "sales", "fiscal", "decisiones", "criminales", "cámaras", "amanecer", "convirtió",
			"aldea", "abandonar", "obvio", "habíamos", "apesta", "abandonado", "volverás", "preocuparse", "kim",
			"favorita", "lanzamiento", "difíciles", "vivido", "roca", "peces", "maravilla", "espacial", "conductor",
			"camarada", "trabajado", "caramba", "sopa", "numero", "gritar", "envía", "casino", "alteza", "karen",
			"identidad", "cambios", "guardar", "gatos", "gané", "arresto", "éstas", "preguntó", "éstos", "probable",
			"seamos", "importaría", "firmar", "federal", "caras", "trabajos", "houston", "hagámoslo", "expediente",
			"tigre", "intentó", "harto", "decidir", "periódicos", "casarse", "lento", "hecha", "daba", "simon",
			"ponerte", "pelotas", "monstruos", "ie", "cruel", "city", "señoritas", "gigante", "dick", "siglos",
			"escalera", "incidente", "escenario", "dirás", "ciertas", "casar", "llamen", "biblia", "reporte", "moda",
			"casada", "reacción", "falla", "castigo", "fácilmente", "caza", "annie", "magnífico", "decidí", "camina",
			"superficie", "procedimiento", "condición", "asalto", "virgen", "verle", "conocimos", "quitar", "donna",
			"tratas", "noble", "laden", "hierba", "excelencia", "asco", "white", "título", "regresaré", "ejercicio",
			"pasaporte", "extraños", "ciento", "afortunado", "sacó", "irán", "sacerdote", "pedirle", "impuestos",
			"crisis", "aventura", "to", "puestos", "pánico", "oz", "llames", "ladrones", "enseñó", "logramos",
			"estuvieron", "últimas", "página", "metro", "grace", "enterprise", "alla", "pudieron", "mostrar", "escribe",
			"ventaja", "enamorada", "boston", "usr", "gorda", "vendrán", "paren", "xena", "piedras", "pesado",
			"despertar", "warren", "pierdes", "jesucristo", "damos", "treinta", "recuerde", "earl", "capacidad",
			"soportar", "senado", "grant", "decidió", "prepárense", "asusta", "aquiles", "aquello", "and", "aguantar",
			"cohen", "aprendí", "tenéis", "parezca", "alegría", "territorio", "física", "escape", "tomes", "sandy",
			"excusa", "estudios", "arthur", "shaw", "septiembre", "personaje", "fábrica", "elegido", "cole", "tanque",
			"salta", "hablaste", "conferencia", "compromiso", "ayudaré", "salieron", "rusos", "parezco", "llevando",
			"exterior", "punta", "ponerse", "pasen", "puerto", "decírtelo", "caro", "asombroso", "pasara", "matarán",
			"maleta", "competencia", "canciones", "miranda", "cinturón", "saint", "papas", "marco", "destrucción",
			"terry", "paraíso", "meta", "alternativa", "socios", "alas", "w", "ocasión", "casarme", "salsa", "romano",
			"prisioneros", "fogg", "encantan", "encantadora", "comercial", "primavera", "metió", "luther", "johnson",
			"volvemos", "mandar", "luce", "sagrado", "nacido", "fabuloso", "echo", "desaparecer", "potencia", "adn",
			"traiga", "permítame", "mayores", "regalos", "pusiste", "peligrosa", "militares", "jodida", "coincidencia",
			"bandera", "adivinar", "wilson", "tubo", "partida", "opciones", "llamaron", "hombro", "vampiros", "sabían",
			"recibió", "marty", "jugadores", "celebrar", "armario", "tetas", "paliza", "sabor", "míralo", "lata",
			"héroes", "aigo", "serpiente", "escribí", "comité", "bendiga", "terminé", "oyó", "imaginación", "funcionó",
			"bello", "hígado", "cierre", "cabina", "queríamos", "pasas", "mantente", "japón", "deseos", "cuéntame",
			"rastro", "compró", "anterior", "acero", "desnudo", "comisario", "catherine", "refuerzos", "preparar",
			"perdidos", "pagaré", "miami", "huevo", "entrando", "armada", "trate", "supo", "bromeas", "verdaderamente",
			"napoleón", "miro", "límite", "esperemos", "sacado", "diamante", "secuestro", "casco", "ayudó", "nene",
			"marcas", "francisco", "pudieras", "poca", "jugo", "heridos", "congreso", "cementerio", "prostituta",
			"desaparece", "consecuencias", "terreno", "salvado", "mueren", "igualmente", "ganamos", "circunstancias",
			"trabajamos", "marie", "jill", "amén", "katie", "funcionando", "evan", "personalidad", "muro", "contestar",
			"razon", "peores", "particular", "llévame", "gimnasio", "garfield", "emocionante", "desgracia",
			"científico", "buscado", "buddy", "v", "terapia", "prepara", "phil", "patrulla", "globo", "despejado",
			"creado", "aprendido", "nací", "lanning", "embajador", "barcos", "posiblemente", "malvado", "dormitorio",
			"despacho", "desagradable", "viniendo", "riddick", "recibo", "nuclear", "mensajes", "mantengan", "ultimo",
			"autoridades", "quedes", "pasaron", "gano", "ayudo", "aprecio", "sentimiento", "popular", "extrañas",
			"detenerlo", "civiles", "tontería", "reyes", "americana", "vuelven", "ventanas", "tiburón", "seiya",
			"peleas", "organización", "jedi", "ayudarla", "tuvieras", "sally", "querrás", "querrá", "kelly",
			"infección", "decente", "logrado", "jersey", "amar", "wayne", "equipos", "canta", "bebés", "pozo", "india",
			"esperado", "enseñar", "darnos", "brown", "tomaste", "internet", "habilidades", "encanto", "dispararon",
			"comprendes", "áfrica", "adorable", "rica", "llegaremos", "harold", "reales", "mismas", "medios",
			"marihuana", "traidor", "martha", "funcione", "clínica", "preferiría", "podían", "olvidó", "fred",
			"entramos", "ejercito", "división", "sector", "perdedor", "patrón", "encontrará", "crema", "barry",
			"planeado", "mantenga", "juguete", "edward", "técnica", "nacimiento", "esperaré", "tim", "termino", "matas",
			"entrado", "destruido", "daremos", "circo", "ascensor", "andrew", "aca", "investigar", "encuentren",
			"archivo", "salve", "presentar", "logro", "intentas", "encargaré", "armado", "apuestas", "maggie",
			"arrestado", "unión", "permanecer", "muéstrame", "móvil", "louise", "juan", "dificil", "casados", "cañón",
			"bruce", "tribunal", "sepan", "satélite", "recuerden", "intentaré", "falsa", "coca", "black", "símbolo",
			"rocas", "putas", "pendejo", "mátalo", "judío", "estando", "subtítulos", "gritando", "dejemos",
			"científicos", "bebiendo", "saque", "navorski", "metal", "despedida", "golf", "empiece", "defender",
			"crecer", "rota", "sentimos", "n", "guerreros", "echa", "comunicación", "probado", "pensarlo", "háblame",
			"detenido", "sabría", "muertes", "movimientos", "prohibido", "pájaros", "kane", "cargar", "willie",
			"podíamos", "junior", "imágenes", "haberse", "acusado", "peleando", "lealtad", "considera", "cazador",
			"buscarlo", "apaga", "objeto", "sofá", "personales", "mac", "homicidio", "electricidad", "advertencia",
			"rumores", "mentir", "fíjate", "deuda", "celoso", "bicicleta", "azules", "visitas", "mejorar", "escudo",
			"cueva", "aérea", "nathan", "levantar", "juguetes", "ciertamente", "bajen", "aaron", "you", "temas",
			"pedirte", "mcdonalds", "elegante", "divorcio", "depósito", "concierto", "calla", "transmisión", "mataría",
			"flor", "wyatt", "viejas", "trabajan", "onda", "saldremos", "preguntado", "aliento", "academia", "sonar",
			"recibe", "patio", "función", "conduce", "benton", "pregúntale", "palo", "internacional", "hueso",
			"heather", "esclavos", "versión", "sacrificio", "judíos", "increible", "in", "fotografía", "delicioso",
			"crímenes", "zorra", "valle", "llevaremos", "lentamente", "encantada", "arreglo", "volvería", "logró",
			"romántico", "preocuparte", "olvido", "fascinante", "cuadro", "creyó", "adultos", "wow", "sobrino",
			"secuencia", "nicky", "lleven", "huh", "entradas", "decirnos", "resistencia", "llamarlo", "greene",
			"ayudas", "esperanzas", "disponible", "civil", "caos", "audrey", "velas", "tenerlo", "ponerle", "muestras",
			"lógica", "casualidad", "sigamos", "rayo", "pato", "pagado", "máscara", "gustado", "buscarte", "selva",
			"pm", "equivoqué", "condenado", "cometido", "west", "travis", "tratan", "pudimos", "preguntando",
			"tragedia", "poli", "oliver", "compañías", "charla", "mete", "malditas", "campos", "cadáveres",
			"básicamente", "vamonos", "trucos", "preparada", "funcionará", "conté", "contando", "construcción",
			"ciudadanos", "agarrar", "infeliz", "francesa", "explica", "decide", "vende", "rutina", "gustar",
			"empieces", "caída", "almorzar", "actividad", "inocentes", "fondos", "coraje", "ayudando", "aterrizar",
			"actuación", "vietnam", "suba", "h", "cynthia", "ayudante", "vuela", "vaca", "dc", "cirujano", "tio",
			"olvídate", "okay", "entregar", "encerrado", "rubia", "kong", "joyas", "empecemos", "buzz", "amado",
			"terminamos", "romanos", "piedad", "llamarte", "hacían", "furia", "detalle", "admitir", "tendrías",
			"respiración", "prepárate", "escribiendo", "confundido", "vigilando", "sufrimiento", "saludos", "robaste",
			"l", "golpear", "garaje", "buscaré", "revolución", "motel", "buscaba", "alianza", "otto", "estupidez",
			"disparado", "sangrando", "quietos", "miserable", "ideal", "empiezo", "controla", "prometiste", "heroína",
			"empezado", "dámelo", "vecindario", "empleado", "conociste", "colores", "cerebral", "plástico", "piano",
			"pensamiento", "lewis", "letra", "empleados", "dosis", "dólar", "benny", "apolo", "agrada", "tocado",
			"tíos", "super", "rifle", "renunciar", "incluyendo", "hoyo", "español", "acepta", "rueda", "municiones",
			"ciudades", "béisbol", "regresado", "pediste", "ja", "sesión", "comprender", "tocando", "montar", "ma",
			"familiares", "espía", "efectos", "salto", "pagó", "impacto", "emoción", "disparos", "comando",
			"autorización", "informes", "honestamente", "existencia", "demasiada", "coches", "clara", "alcanzar",
			"volviste", "octubre", "g", "conseguimos", "barato", "apostar", "requiere", "importan", "grano",
			"demasiados", "casarte", "tráeme", "rick", "progreso", "odias", "ocurrir", "hacías", "serías", "secretario",
			"rusia", "piénsalo", "gatito", "déme", "colección", "vais", "sufrido", "ningun", "helena", "harris",
			"terror", "salva", "salgas", "sacarte", "lanza", "usó", "quince", "prometido", "preciso", "pirata", "park",
			"moral", "moneda", "kyle", "hare", "haberle", "estuvieras", "dijimos", "cayendo", "bajas", "viniera",
			"soñando", "servirá", "moscú", "mínimo", "hermosas", "escondido", "eric", "demostrar", "perrito", "jordan",
			"intente", "habrán", "experimento", "espaldas", "diseño", "barra", "anciano", "alfombra", "actriz", "tono",
			"sentarse", "remedio", "listas", "gris", "enviaron", "entendiste", "disfrutar", "contaré", "comen",
			"sentada", "pregunte", "japonés", "gritos", "dolares", "asegurarme", "restos", "piper", "pierdo", "motivos",
			"josh", "dimos", "campeonato", "apúrense", "unico", "reír", "prima", "maricón", "actúa", "periodista",
			"luchando", "estelar", "escritor", "encontrarás", "dejando", "adecuado", "terrorista", "lady", "hierro",
			"enterrado", "big", "sincero", "sharon", "representa", "positivo", "mereces", "italia", "fea", "acerques",
			"petróleo", "encargado", "capítulo", "enseñaré", "cuentos", "breve", "atreves", "súbete", "sed", "patético",
			"mostrarte", "doctores", "carácter", "tuyos", "plaza", "obispo", "necesite", "muevan", "involucrado",
			"frankie", "darán", "robin", "ingleses", "usas", "ruedas", "prácticamente", "orejas", "golpeó", "costado",
			"conducta", "toman", "suban", "médica", "manzana", "jonathan", "escuadrón", "reconozco", "ordenes",
			"gallina", "cambiando", "borde", "ataques", "actual", "víctor", "polly", "pecados", "invito",
			"inconsciente", "bastardos", "abrió", "shaun", "oyeron", "intentaba", "cuartel", "consciente",
			"tranquilízate", "suyos", "residencia", "máxima", "llevarla", "grasa", "células", "retrocedan", "recepción",
			"medicinas", "explotar", "corona", "callate", "calidad", "ayúdeme", "asesina", "absurdo", "revés",
			"producto", "nancy", "encontrarla", "dj", "descansa", "copias", "apagar", "profesionales", "práctica",
			"investigando", "francamente", "calvin", "afganistán", "vecino", "trozo", "tómate", "tercero", "sirven",
			"saquen", "manual", "enciende", "cercano", "bailando", "atras", "usarlo", "telefono", "registros", "ponlo",
			"pasta", "lleguen", "saliste", "sabrás", "p", "morgan", "limpieza", "estadounidense", "enojada",
			"desafortunadamente", "conoció", "pinta", "matamos", "funcionan", "cocinar", "cima", "atractivo",
			"preguntes", "necesitaré", "lleguemos", "interrumpir", "haberla", "apropiado", "presentimiento",
			"intentamos", "finales", "evitarlo", "enviaré", "deporte", "bajando", "sucia", "recibimos", "pastillas",
			"ceremonia", "tina", "muelle", "maletas", "k", "hijas", "desconocido", "conseguiré", "taylor", "refugio",
			"imagina", "franceses", "convertirse", "arruinado", "vomitar", "pasajeros", "normales", "disfraz",
			"continúe", "arreglado", "aprende", "venimos", "tenerte", "seguirá", "razonable", "puesta", "masaje",
			"lágrimas", "gustaria", "extranjero", "dejaremos", "carlos", "apellido", "alguacil", "tracy", "temporal",
			"rompe", "mayo", "egoísta", "aceite", "platos", "miércoles", "indios", "decimos", "creasy", "bolsas",
			"anuncio", "tirado", "notado", "mutantes", "imaginas", "habitaciones", "grecia", "entré", "díselo", "césar",
			"stanley", "holly", "comprado", "cocaína", "amarillo", "acabará", "suceda", "mentes", "maldad", "gustará",
			"eliminar", "corbata", "burke", "atrapados", "sabíamos", "merezco", "medalla", "lograste", "lionel",
			"jaula", "dió", "chofer", "informado", "fox", "fatal", "enfadado", "cubierto", "compañera", "ronda",
			"regina", "cobra", "cantante", "abrazo", "tarjetas", "sentarme", "refería", "indica", "gravedad", "ganando",
			"estúpidos", "elecciones", "collar", "cincuenta", "programas", "naturalmente", "embajada", "desayunar",
			"dejaría", "vigila", "quisieras", "intereses", "agárrate", "aéreo", "traición", "seguiré", "salvajes",
			"prefiere", "podéis", "noel", "mueres", "molestar", "italiano", "dejarla", "corazones", "averiguarlo",
			"ave", "arreglarlo", "advierto", "vendiendo", "pública", "newman", "monje", "jugamos", "hablarme",
			"escoger", "andas", "ví", "teddy", "pico", "llevé", "entro", "tenias", "priorato", "perdemos", "industria",
			"cretino", "cafetería", "cabaña", "aparecer", "tradición", "suelto", "publicidad", "pares", "oídos",
			"medida", "japoneses", "hitler", "dieta", "capa", "vendrás", "sincronizado", "neil", "historial", "guión",
			"enano", "devolver", "solitario", "misiles", "hall", "desnuda", "bridget", "brad", "vaquero", "únicos",
			"trasera", "sano", "piezas", "liga", "gerente", "cindy", "caridad", "armados", "trajeron", "religión",
			"jon", "fotografías", "sentirse", "rezar", "protege", "preparen", "niñera", "naranja", "matarnos", "jugada",
			"gandalf", "espérame", "enseñarte", "cohete", "tanques", "sombras", "moverse", "llegara", "cortó",
			"cierren", "volverán", "usamos", "sabremos", "potter", "maletín", "lenguaje", "jenna", "invitación",
			"cambian", "rehenes", "margaret", "freddy", "encontremos", "descubrí", "audiencia", "agenda", "wallace",
			"sígueme", "sensible", "sammy", "reverendo", "queréis", "dex", "cazar", "bodas", "seguiremos", "rosas",
			"retiro", "primeras", "poderosa", "matthew", "deberia", "cantando", "antiguos", "teme", "smallville",
			"síganme", "moviendo", "juventud", "equivocas", "trabajadores", "seguí", "salvaste", "pulmones", "payaso",
			"oírlo", "fama", "engaño", "distrito", "disculparme", "davis", "ciudadano", "robando", "equipaje",
			"ciencias", "ubicación", "federales", "tratamos", "ofrece", "of", "junio", "extremadamente", "actuando",
			"traducción", "supiste", "sacarlo", "preparando", "mei", "letras", "típico", "reserva", "propuesta",
			"plataforma", "mago", "linea", "hank", "dude", "busque", "tabaco", "principios", "lou", "dormida", "costó",
			"causar", "temer", "subiendo", "rollo", "ns", "hechizo", "gusano", "guau", "grupos", "discos", "despierte",
			"st", "ocurriendo", "horario", "expedientes", "envié", "dibujos", "concurso", "colt", "autopista", "atacó",
			"vendedor", "sigas", "rebeldes", "radar", "madame", "expresión", "escuchan", "carla", "use", "resultó",
			"principe", "hágalo", "greg", "exposición", "demasiadas", "cuidando", "aterrizaje", "apetece", "tomaron",
			"sorprendente", "responda", "olvidaste", "lobos", "echado", "deberias", "cortado", "citas", "abiertos",
			"zapato", "preso", "lastimar", "explosivos", "colegas", "telly", "supieras", "secretaria", "queridos",
			"morirás", "mickey", "cuero", "confesión", "círculo", "sabéis", "química", "obras", "llores", "juegas",
			"invisible", "fase", "esconde", "capitan", "adulto", "vacía", "siéntense", "pastor", "llegada", "jesse",
			"gatillo", "debamos", "teléfonos", "saqué", "regresará", "olvidaré", "lucas", "límites", "jovencita",
			"hong", "dibujo", "británico", "balboa", "psiquiatra", "objetos", "negociar", "hill", "formar", "fiel",
			"diosa", "descubrió", "ala", "abril", "saldré", "mías", "disfruta", "atender", "tomaremos", "samantha",
			"rosie", "oreja", "necesitaremos", "motores", "intenciones", "hannah", "cumplido", "brindis", "villa",
			"trono", "traen", "thunderbird", "sentados", "samurai", "salía", "raras", "producción", "medias", "lavar",
			"jazz", "extraordinario", "causado", "angie", "serpientes", "revisa", "quedate", "nervios", "estable",
			"cerdos", "riesgos", "pasaste", "michelle", "madres", "instinto", "episodio", "empezaron", "aniversario",
			"angela", "anderson", "viera", "viajes", "trabajé", "rango", "preocupación", "métete", "imaginé", "hayamos",
			"fenómeno", "dolores", "demanda", "damien", "creó", "asegurar", "pesadillas", "niega", "molestia", "kirk",
			"extremo", "debilidad", "compasión", "camiones", "aproximadamente", "afecta", "administración",
			"reconocimiento", "recientemente", "practicar", "pierdas", "pandilla", "miss", "mates", "disparando",
			"británicos", "ayudarlos", "abby", "sueldo", "siguió", "significado", "miente", "medidas", "guitarra",
			"custodia", "boletos", "ayudado", "sydney", "sentarte", "risa", "quemar", "prometió", "observando",
			"lectura", "jódete", "considerado", "cometer", "cambié", "asientos", "wong", "shock", "satisfecho",
			"saludable", "lanzar", "ken", "hoja", "conozca", "concepto", "colgar", "apunta", "renta", "prefieres",
			"pediré", "mediodía", "influencia", "hacernos", "figura", "ensayo", "coordenadas", "casó", "bart", "albert",
			"político", "países", "once", "matarlos", "karl", "graduación", "frase", "eterna", "estarlo", "descuida",
			"toalla", "próximos", "monedas", "mirad", "miraba", "jesus", "griegos", "famosa", "corredor", "atravesar",
			"aguas", "modales", "guerras", "eternidad", "conocerla", "charlotte", "busquen", "buck", "acuerda",
			"sentirme", "ralph", "quema", "poema", "ordenó", "espiritual", "costumbre", "comencé", "cabra", "alquiler",
			"williams", "vencido", "vela", "trataré", "sospechosos", "quinto", "pilotos", "philip", "perdieron",
			"norman", "josé", "generación", "formación", "estructura", "concéntrate", "complejo", "voces", "virginia",
			"tragos", "temor", "república", "recursos", "presentación", "pirámide", "oficialmente", "maquillaje",
			"guardaespaldas", "escucharme", "dejara", "contiene", "bendición", "vuestros", "semejante", "pague",
			"molestes", "cultura", "comió", "antecedentes", "tendríamos", "representante", "prestado", "posesión",
			"oir", "nubes", "meterte", "guantes", "esperabas", "enseña", "encontrarme", "consejos", "regreses",
			"preocupen", "pistolas", "jennifer", "gane", "dana", "sos", "niveles", "lane", "ganaste", "considerando",
			"capital", "cambie", "ánimo", "utilizar", "lograrlo", "llévate", "gafas", "espíritus", "do", "watson",
			"ruby", "preguntaste", "pagará", "maíz", "ho", "estacionamiento", "contactos", "aguja", "turner", "trenes",
			"soporto", "sacaré", "oido", "fortaleza", "donald", "corea", "conversar", "computadoras", "cálmese",
			"volante", "sucederá", "jessica", "decías", "data", "cuente", "civilización", "cabrones", "arruinar",
			"vampiro", "tocó", "regresé", "pases", "mitch", "megan", "logan", "fantástica", "facil", "estabamos",
			"diera", "detenga", "violación", "pam", "hemorragia", "fianza", "docena", "centavo", "célula", "carreras",
			"callado", "rodilla", "prometes", "profundamente", "posiciones", "hicieras", "furioso", "desperté", "comes",
			"ciega", "cicatriz", "bebidas", "acércate", "tiró", "policial", "pasear", "masa", "llegues", "global",
			"escapado", "dinos", "cuarta", "bienes", "aparato", "steven", "sostén", "realizar", "permitiré",
			"orgullosos", "corten", "comisión", "vidrio", "presa", "ocupados", "morfina", "habrían", "columna",
			"sacaron", "patas", "oírme", "ocultar", "objeción", "myers", "maneja", "largas", "generalmente", "fuentes",
			"cubrir", "ataca", "tenis", "santos", "potencial", "plano", "murphy", "levante", "escala", "enfrentar",
			"cuánta", "sobra", "rumor", "páginas", "órganos", "león", "entras", "decían", "chinos", "brujas", "barbara",
			"admiro", "voto", "volvieron", "suelte", "reto", "metes", "infarto", "cuyo", "construyó", "boleto", "besar",
			"portal", "ponemos", "monte", "discusión", "digno", "desean", "darse", "considerar", "bancos", "poción",
			"perdóneme", "pensabas", "mónica", "generoso", "estatua", "convencer", "conocida", "comenzamos", "aceptado",
			"woody", "rob", "paige", "importar", "gabrielle", "fracaso", "ciertos", "bomberos", "zé", "sorprendido",
			"protegido", "presenta", "metí", "entenderlo", "detengan", "conflicto", "códigos", "seco", "sabiendo",
			"hacerla", "green", "graves", "dejarás", "convencido", "comprende", "bromas", "yeah", "oyen", "moleste",
			"mencionar", "gastos", "estaríamos", "esperaremos", "encontrarán", "billetes", "arco", "altos", "actores",
			"talvez", "sitios", "saluda", "quejas", "planeando", "moriremos", "marica", "habrías", "estarían",
			"dejarán", "castle", "amantes", "acostumbrado", "tyler", "torneo", "planos", "ordeno", "morris", "miré",
			"maldicion", "kg", "dirías", "confirmado", "vacas", "quirófano", "ponerlo", "origen", "fusión", "deberá",
			"verdaderos", "serían", "seguía", "sabrán", "pasaje" };
	
}