package org.example.opgave_shoppingcart.Controller;

import org.example.opgave_shoppingcart.Model.Cart;
import org.example.opgave_shoppingcart.Model.CartItem;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private Cart cart; // reference til indkøbskurv session


    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {

        // check om attributten ‘cart’ findes i sessions objektet
        Cart cart = (Cart) session.getAttribute("cart");

        // hvis ikke - opret en ny indkøbskurv (cart)
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // opret indkøbskurvattributten (cart) i session objektet
        model.addAttribute("cart", cart);
        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.getTotal());

        // sæt sessionslevetiden til 30 sec til testformål
        session.setMaxInactiveInterval(30);

        // tilføj attributterne ‘items’, og ‘total’ til model objektet

        // returner cart.html, der viser ui
        return "cart";

    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String name, @RequestParam double price, @RequestParam int quantity, HttpSession session) {

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // opret nyt item
        CartItem item = new CartItem(name, price, quantity);

        // tilføj item til cart
        cart.addItem(item);
        // redirect til /cart
        return "redirect:/cart";

    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam int index, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart");

        // slet item på ‘index’ i cart
        if (cart != null && index >= 0 && index < cart.getItems().size()) {
            cart.removeItem(index);
        }

        // redirect til /cart
        return "redirect:/cart";
    }

    @PostMapping("/cart/empty")
    public String emptyCart(HttpSession session) {

        // fjern ‘cart’ attribute fra session objekt
        session.removeAttribute("cart");

        // redirect til /cart
        return "redirect:/cart";

    }
}