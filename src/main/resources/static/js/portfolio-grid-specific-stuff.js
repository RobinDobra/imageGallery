$(document).on('ready', function () {
    // INITIALIZATION OF HEADER
    // =======================================================
    var header = new HSHeader($('#header')).init();


    // INITIALIZATION OF MEGA MENU
    // =======================================================
    var megaMenu = new HSMegaMenu($('.js-mega-menu'), {
        desktop: {
            position: 'left'
        }
    }).init();


    // INITIALIZATION OF UNFOLD
    // =======================================================
    var unfold = new HSUnfold('.js-hs-unfold-invoker').init();


    // INITIALIZATION OF SHOW ANIMATIONS
    // =======================================================
    $('.js-animation-link').each(function () {
        var showAnimation = new HSShowAnimation($(this)).init();
    });


    // INITIALIZATION OF FORM VALIDATION
    // =======================================================
    $('.js-validate').each(function() {
        $.HSCore.components.HSValidation.init($(this), {
            rules: {
                confirmPassword: {
                    equalTo: '#signupPassword'
                }
            }
        });
    });


    // INITIALIZATION OF CUBEPORTFOLIO
    // =======================================================
    $('.cbp').each(function () {
        var cbp = $.HSCore.components.HSCubeportfolio.init($(this), {
            displayTypeSpeed: 0
        });
    });

    $('.cbp').on('initComplete.cbp', function() {
        // INITIALIZATION OF STICKY BLOCKS
        // =======================================================
        $('.js-sticky-block').each(function () {
            var stickyBlock = new HSStickyBlock($(this)).init();
        });
    });


    // INITIALIZATION OF GO TO
    // =======================================================
    $('.js-go-to').each(function () {
        var goTo = new HSGoTo($(this)).init();
    });
});

if (/MSIE \d|Trident.*rv:/.test(navigator.userAgent)) document.write('<script src="../vendor/babel-polyfill/dist/polyfill.js"><\/script>');
