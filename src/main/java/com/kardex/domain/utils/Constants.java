package com.kardex.domain.utils;

import com.kardex.domain.model.CartItem;

import java.util.List;

public class Constants {

    //Controller

    // Base paths
    public static final String ORDERS_BASE_PATH = "/orders";
    public static final String ORDER_ID_PATH = "/{orderId}";
    public static final String GET_ORDER_ID_PATH = "/{orderId}/{tokenOrder}";
    public static final String GET_ORDER_HISTORY_PATH = "/{orderId}/orderHistory";
    public static final String CART_BASE_PATH = "/cart";
    public static final String ADD_ITEM_CART_PATH = "/add";
    public static final String REMOVE_ITEM_CART_PATH = "/remove/{productId}";
    public static final String WEBHOOK_BASE_PATH = "/webhook/paypal";
    public static final String PAYPAL_BASE_PATH = "/api/paypal";
    public static final String CREATE_ORDER = "/create-order";

    // HTTP Status Codes
    public static final String CREATED = "201";
    public static final String CONFLICT = "409";
    public static final String OK = "200";
    public static final String NOT_FOUND = "404";

    // Default Pagination Values
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_ASC = "true";
    public static final String SORT_BY_PRODUCT_NAME = "product.name";
    public static final String SORT_BY_PROVIDER_NAME = "product.provider.companyName";

    //Dto
    public static final String PRODUCT_ID_NOT_BLANK_MESSAGE = "The product id cannot be blank";
    public static final String QUANTITY_POSITIVE_MESSAGE = "Quantity must be greater than zero";
    public static final String QUANTITY_NOT_BLANK_MESSAGE = "The quantity cannot be blank";

    //Cors
    public static final String CORS_ALLOWED_PATHS = "/**";
    public static final String CORS_ALLOWED_ORIGIN = "*";
    public static final String[] CORS_ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
    public static final Long DEFAULT_STATUS_ID = 1L;

    // Correo para el proveedor
    public static String EMAIL_PROVIDER_CONTENT(Long orderId, String numberOrder, String tokenOrder, List<CartItem> cartItems) {
        // Generar el enlace de seguimiento
        String trackingLink = "http://127.0.0.1:5501/View/vistaProveedores.html?idOrder=" + orderId + "&tokenOrder=" + tokenOrder;

        // Iniciar contenido del correo
        StringBuilder emailContent = new StringBuilder("<div style='max-width: 600px; margin: auto; font-family: Arial, sans-serif; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);'>"
                + "<div style='background: linear-gradient(135deg, #0CACAB, #0BE0A4); padding: 20px; text-align: center; color: white;'>"
                + "    <h1 style='margin: 0;'>🚚 Nuevo Pedido Pendiente de Gestión</h1>"
                + "</div>"
                + "<div style='padding: 20px; text-align: center;'>"
                + "    <p style='font-size: 18px; color: #333;'>Hola,</p>"
                + "    <p style='font-size: 16px; color: #555;'>Un nuevo pedido ha sido realizado con el número de orden <b style='color: #0BE0A4; font-size: 20px;'>" + numberOrder + "</b>.</p>"
                + "    <p style='font-size: 16px; color: #555;'>Por favor, revisa los productos a continuación y actualiza el estado del pedido haciendo clic en el siguiente botón:</p>"
                + "    <a href='" + trackingLink + "' style='display: inline-block; padding: 12px 25px; font-size: 18px; background: #0CACAB; color: white; text-decoration: none; border-radius: 8px; font-weight: bold; transition: background 0.3s;'>📍 Gestionar Pedido</a>"
                + "    <p style='margin-top: 20px; font-size: 14px; color: #777;'>Si no eres el encargado de este pedido, por favor ignora este mensaje.</p>"
                + "</div>"
                + "<div style='padding: 20px; text-align: center;'>"
                + "    <h3 style='font-size: 18px; color: #333;'>Detalles del Pedido:</h3>");

        // Listar los productos del pedido
        emailContent.append("<ul style='list-style-type: none; padding: 0; color: #555;'>");
        for (CartItem item : cartItems) {
            emailContent.append("<li style='font-size: 16px; margin-bottom: 10px;'><b>").append(item.getProduct().getName()).append("</b><br>")
                    .append("Cantidad: ").append(item.getQuantity()).append("</li>");
        }
        emailContent.append("</ul>");

        // Final del contenido
        emailContent.append("</div>" + "<div style='background: #f4f4f4; padding: 10px; text-align: center; font-size: 14px; color: #888;'>"
                + "    <p>Gracias por tu colaboración. 📦</p>" + "</div>" + "</div>");

        return emailContent.toString();
    }

    // Correo para el usuario
    public static String EMAIL_USER_CONTENT(List<String> productNames) {
        // Iniciar el contenido del correo
        StringBuilder emailContent = new StringBuilder("<div style='max-width: 600px; margin: auto; font-family: Arial, sans-serif; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);'>"
                + "<div style='background: linear-gradient(135deg, #1AF47D, #B2E5EC); padding: 20px; text-align: center; color: white;'>"
                + "    <h1 style='margin: 0; font-size: 28px;'>🛍️ ¡Pedido confirmado!</h1>"
                + "</div>"
                + "<div style='padding: 20px; text-align: center;'>"
                + "    <p style='font-size: 18px; color: #333;'>Hola,</p>"
                + "    <p style='font-size: 16px; color: #555;'>¡Tu pedido ha sido registrado con éxito! Nos alegra que hayas elegido nuestros productos.</p>"
                + "    <p style='font-size: 16px; color: #555;'>A continuación, te mostramos los productos que has pedido:</p>");

        // Agregar los productos al contenido del correo
        emailContent.append("<ul style='list-style-type: none; padding: 0; color: #555;'>");
        for (String productName : productNames) {
            emailContent.append("<li style='font-size: 16px; margin-bottom: 10px;'>" + "• ").append(productName).append("</li>");
        }
        emailContent.append("</ul>");

        // Agregar mensaje final y footer
        emailContent.append("<p style='margin-top: 20px; font-size: 14px; color: #777;'>Te avisaremos cuando se actualice el estado de tu pedido en la pagina web.</p>"
                + "    <p style='font-size: 14px; color: #777;'>Si tienes alguna pregunta, no dudes en contactarnos.</p>"
                + "</div>" + "<div style='background: #f4f4f4; padding: 10px; text-align: center; font-size: 14px; color: #888;'>"
                + "    <p>¡Gracias por tu confianza! 💙</p>" + "</div>" + "</div>");

        return emailContent.toString();
    }

    private Constants() {

    }
}
