document.addEventListener("keypress", function(event) {
    if (event.key === "/") {
        event.preventDefault();
        $('#id').focus()
    }
});