@import dto.BasePage
@param BasePage page
@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <body>
        @if( page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-warning" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <section>
            <form class="form-horizontal" action="/login" method="post">
                <fieldset>
                    <legend>Регистрация</legend>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="email-input">Введи email</label>
                        <div class="col-md-4">
                            <input id="email-input" name="email" type="email" placeholder="email" class="form-control input-md">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="password-input">Введи пароль</label>
                        <div class="col-md-4">
                            <input id="password-input" name="password" type="password" placeholder="password" class="form-control input-md">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="button"></label>
                        <div class="col-md-4">
                            <button id="button" name="button" class="btn btn-success">Войти</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </section>
        </body>
    </main>
`
)