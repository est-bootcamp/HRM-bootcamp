// ControlSidebar.js
document.addEventListener("DOMContentLoaded", function () {
    (function ($) {
        'use strict';
        console.log("✅ ControlSidebar.js loaded, jQuery version:", $.fn.jquery);

        $('[data-widget="pushmenu"]').on('click', function () {
            console.log("✅ Pushmenu clicked");
        });

    })(jQuery);
});