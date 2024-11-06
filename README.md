# Instrucciones para Ejecutar el Proyecto

1. **Descargar e Importar el Proyecto en Eclipse**
   - Clona o descarga el proyecto y ábrelo en Eclipse.

2. **Descargar Dependencias de Maven**
   - Asegúrate de que Maven descargue todas las dependencias necesarias. Esto se puede hacer automáticamente cuando Eclipse importa el proyecto.

3. **Configurar las Credenciales de Redis**
   - En el archivo `application.properties`, ubicado en la carpeta `resources`, agrega las credenciales de Redis (Host, Contraseña, etc.):
     ```properties
     spring.redis.host=localhost
     spring.redis.password=tu_contraseña
     ```

4. **Ejecutar el Proyecto como Aplicación Spring Boot**
   - Haz clic derecho en el proyecto y selecciona `Run As > Spring Boot App`.
   - Luego, selecciona el archivo `Main` que contiene la clase con el método `main`.

5. **Ver los Resultados en la Consola**
   - Los resultados se mostrarán en la consola. Si ejecutas el proyecto nuevamente dentro de los 60 segundos, los datos se cargarán directamente desde Redis, mejorando la velocidad de acceso.