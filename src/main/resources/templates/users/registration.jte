
@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <body>
        <section>
            <form class="form-horizontal" action="/registration" method="post">
                <fieldset>
                    <!-- Form Name -->
                    <legend>Регистрация</legend>
                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="name-input">Введи имя</label>
                        <div class="col-md-4">
                            <input id="name-input" name="name" type="text" placeholder="не менее 4 символов" class="form-control input-md">
                        </div>
                    </div>
                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="email-input">Введи email</label>
                        <div class="col-md-4">
                            <input id="email-input" name="email" type="email" placeholder="email" class="form-control input-md">
                        </div>
                    </div>
                    <!-- Password input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="password-input">Введи пароль</label>
                        <div class="col-md-4">
                            <input id="password-input" name="password" type="password" placeholder="не менее 6 символов" class="form-control input-md">
                        </div>
                    </div>
                    <!-- Button -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="button"></label>
                        <div class="col-md-4">
                            <button type="submit" id="button" name="registration" class="btn btn-success">Регистрация</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </section>
        </body>
    </main>
`
)